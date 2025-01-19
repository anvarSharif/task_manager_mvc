package com.example.task_manager_mvc.controller;

import com.example.task_manager_mvc.entity.*;
import com.example.task_manager_mvc.payload.StatusDTO;
import com.example.task_manager_mvc.payload.TaskDTO;
import com.example.task_manager_mvc.repo.*;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
    public String createTask(@ModelAttribute TaskDTO taskDTO, MultipartFile file) throws IOException {
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
        Task task = new Task(
                taskDTO.getTitle(),
                taskDTO.getDescription(),
                status,
                attachment,
                selectedUsers,
                taskDTO.getDeadline()
        );
        taskRepository.save(task);
        return "redirect:/";
    }

    @PostMapping("/edite")
    public String editeTask(@ModelAttribute TaskDTO taskDTO) {

        return "redirect:/";
    }

    @PostMapping("/comment")
    public String addComment(Model model, @RequestParam UUID taskId, @RequestParam String commentText) {

        List<User> users = userRepository.findAll();
        User user = users.get(1);

        Task task = taskRepository.findById(taskId).orElseThrow();

        Comment comment = new Comment(
                null,
                commentText,
                task,
                user,
                LocalDateTime.now()
        );
        commentRepository.save(comment);
        return "redirect:/task/" + taskId;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam UUID taskId) {
        taskRepository.deleteById(taskId);
        return "redirect:/";
    }

    @PostMapping("/editeStatus")
    public String editeStatus(@RequestParam UUID taskId, @RequestParam String action) {
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
                return "redirect:/";
            }
        }

        return "redirect:/";
    }
}
