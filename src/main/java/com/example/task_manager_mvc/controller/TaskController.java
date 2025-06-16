package com.example.task_manager_mvc.controller;

import com.example.task_manager_mvc.entity.*;
import com.example.task_manager_mvc.payload.StatusDTO;
import com.example.task_manager_mvc.payload.TaskDTO;
import com.example.task_manager_mvc.repo.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @PostMapping("/create")
    public String createTask(@Valid @ModelAttribute TaskDTO taskDTO, BindingResult bindingResult, MultipartFile file,Model model,@RequestParam UUID selectedUserId) throws IOException {
        model.addAttribute("userId",selectedUserId);
        if (bindingResult.hasErrors()){
            model.addAttribute("taskDTO",taskDTO);
            model.addAttribute("selectedUserId",selectedUserId);
            if (taskDTO.getTaskId()!=null){
                Task task = taskRepository.findById(taskDTO.getTaskId()).orElseThrow();
                task.setTitle("");
                model.addAttribute("task", task);
            }
            model.addAttribute("users", userRepository.findAll());
            if (taskDTO.getStatusId()!=null){
                model.addAttribute("statusId", taskDTO.getStatusId());
            }
            return "addTask";
        }

        List<User> selectedUsers = new ArrayList<>();
        if (taskDTO.getSelectedUsers() != null) {
            selectedUsers = userRepository.findAllById(taskDTO.getSelectedUsers());
        }
        Status status = statusRepository.findById(taskDTO.getStatusId()).orElseThrow();
        Attachment attachment = null;
        if (!file.isEmpty()) {

            attachment = new Attachment(file.getOriginalFilename());
            attachmentRepository.save(attachment);
            AttachmentContent attachmentContent = new AttachmentContent(
                    attachment,
                    file.getBytes()
            );
            attachmentContentRepository.save(attachmentContent);
        }
        if (taskDTO.getTaskId()!=null&& file.isEmpty()){
            Task task = taskRepository.findById(taskDTO.getTaskId()).orElseThrow();
            attachment=task.getFile();
        }
        UUID taskId = null;
        if (taskDTO.getTaskId() != null) {
            taskId = taskDTO.getTaskId();
        }
        Task task = new Task(
                taskId,
                taskDTO.getTitle(),
                taskDTO.getDescription(),
                status,
                attachment,
                selectedUsers,
                taskDTO.getDeadline()
        );
        taskRepository.save(task);
        return "redirect:/?userId="+selectedUserId;
    }

    @PostMapping("/comment")
    public String addComment( @RequestParam UUID taskId,@AuthenticationPrincipal User user, @RequestParam String commentText,@RequestParam UUID selectedUserId,Model model) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        model.addAttribute("selectedUserId",selectedUserId);
        Comment comment = new Comment(
                null,
                commentText,
                task,
                user,
                LocalDateTime.now()
        );
        commentRepository.save(comment);
        return "redirect:/task/" + taskId+"?selectedUserId="+selectedUserId;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam UUID taskId,@RequestParam UUID selectedUserId) {
        taskRepository.deleteById(taskId);
        return "redirect:/?userId="+selectedUserId;
    }

    @PostMapping("/editeStatus")
    public String editeStatus(@RequestParam UUID taskId, @RequestParam String action,@RequestParam UUID selectedUserId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        List<Status> statusList = statusRepository.getFilteredStatuses();
        Optional<Status> byIsCompleted = statusRepository.findByIsCompletedTrue();
        if (byIsCompleted.isPresent()) {
            statusList.add(byIsCompleted.get());
        }
        for (int i = 0; i < statusList.size(); i++) {
            if (statusList.get(i).getId().equals(task.getStatus().getId())) {
                if (action.equals("minus")) {
                    task.setStatus(statusList.get(i - 1));
                } else if (action.equals("plus")) {
                    task.setStatus(statusList.get(i + 1));
                    if (task.getStatus().getIsCompleted()) {
                        task.setDeadline(null);
                    }
                }
                taskRepository.save(task);
                return "redirect:/?userId="+selectedUserId;
            }
        }

        return "redirect:/";
    }
}
