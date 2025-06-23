package com.example.task_manager_mvc.controller;

import com.example.task_manager_mvc.entity.Comment;
import com.example.task_manager_mvc.entity.Status;
import com.example.task_manager_mvc.entity.Task;
import com.example.task_manager_mvc.entity.User;
import com.example.task_manager_mvc.payload.*;
import com.example.task_manager_mvc.repo.CommentRepository;
import com.example.task_manager_mvc.repo.StatusRepository;
import com.example.task_manager_mvc.repo.TaskRepository;
import com.example.task_manager_mvc.repo.UserRepository;
import com.example.task_manager_mvc.specification.TaskFilterSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;
    private final CommentRepository commentRepository;


    @GetMapping
    public String getHome(Model model, @AuthenticationPrincipal User currentUser, @ModelAttribute TaskFilter taskFilter) {
        LocalDate today = LocalDate.now();
        model.addAttribute("today", today);
        model.addAttribute("threeDaysLater", today.plusDays(4));
        if (taskFilter.getUserId() == null) {
            taskFilter.setUserId(taskFilter.getSelectedUserId());
        }
        if (taskFilter.getUserId() != null && taskFilter.getUserId().equals(UUID.fromString("99bf70ac-c43b-47fa-83bb-ac38c0c72ae4"))) {
            taskFilter.setUserId(null);
            taskFilter.setIsAll(true);
        } else if (taskFilter.getUserId() == null && taskFilter.getSelectedUserId() == null) {
            taskFilter.setUserId(currentUser.getId());
        }
        Specification<Task> spec = TaskFilterSpecification.taskSpecification(taskFilter);
        model.addAttribute("tasks", taskRepository.findAll(spec));
        model.addAttribute("users", userRepository.findAll());
        if (taskFilter.getIsAll()) {
            taskFilter.setUserId(UUID.fromString("99bf70ac-c43b-47fa-83bb-ac38c0c72ae4"));
            taskFilter.setIsAll(false);
        }
        model.addAttribute("filter", taskFilter);
        List<Status> statusList = statusRepository.getFilteredStatuses();
        Optional<Status> byIsCompleted = statusRepository.findByIsCompletedTrue();

        if (byIsCompleted.isPresent()) {
            statusList.add(byIsCompleted.get());
        }
        model.addAttribute("statuses", statusList);
        return "home";
    }

    @GetMapping("/status/create")
    public String getAddStatus(Model model, @RequestParam UUID selectedUserId) {
        model.addAttribute("selectedUserId", selectedUserId);
        model.addAttribute("endStatusOrder", statusRepository.getEndStatusOrder());
        Optional<Status> byIsCompleted = statusRepository.findByIsCompletedTrue();
        if (!byIsCompleted.isEmpty()) {
            model.addAttribute("completedStatus", byIsCompleted.get());
        }
        model.addAttribute("statusDTO", new StatusDTO());
        return "addStatus";
    }


    @GetMapping("/status/edite")
    public String getEditeStatus(Model model, @RequestParam(required = false) UUID statusId, @RequestParam UUID selectedUserId) {
        model.addAttribute("statusDTO", new StatusDTO());
        model.addAttribute("selectedUserId", selectedUserId);
        model.addAttribute("endStatusOrder", statusRepository.getEndStatusOrder());
        Optional<Status> byIsCompleted = statusRepository.findByIsCompletedTrue();
        byIsCompleted.ifPresent(status -> model.addAttribute("completedStatus", status));
        if (statusId != null) {
            Status status = statusRepository.findById(statusId).orElseThrow();
            model.addAttribute("status", status);
        }
        return "addStatus";
    }

    @GetMapping("/task/edit")
    public String getEditeTask(Model model, @RequestParam UUID taskId, @RequestParam UUID selectedUserId) {
        model.addAttribute("selectedUserId", selectedUserId);
        Task task = taskRepository.findById(taskId).orElseThrow();
        model.addAttribute("task", task);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("taskDTO", new TaskDTO());
        return "addTask";
    }

    @GetMapping("/task/create")
    public String getAddTask(Model model, @RequestParam(required = false) UUID statusId, @RequestParam UUID selectedUserId) {
        model.addAttribute("selectedUserId", selectedUserId);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("statusId", statusId);
        model.addAttribute("taskDTO", new TaskDTO());
        return "addTask";
    }


    @GetMapping("/task/{taskId}")
    public String getTask(Model model, @PathVariable UUID taskId, @RequestParam UUID selectedUserId) {
        model.addAttribute("selectedUserId", selectedUserId);
        Task task = taskRepository.findById(taskId).orElseThrow();
        model.addAttribute("task", task);
        List<Comment> comments = commentRepository.findByTask(task);
        model.addAttribute("comments", comments);
        return "task";
    }

    @GetMapping("/report/criminals")
    public String reportCriminals(Model model, @RequestParam UUID selectedUserId) {
        model.addAttribute("selectedUserId", selectedUserId);
        List<CriminalUserDTO> criminalUser = userRepository.getCriminalUser();
        model.addAttribute("users", criminalUser);
        return "criminals";
    }

    @GetMapping("/report/developer")
    public String reportDeveloper(Model model, @RequestParam UUID selectedUserId) {
        model.addAttribute("selectedUserId", selectedUserId);
        List<UserDTO> users = userRepository.getResultTaskForUser();
        model.addAttribute("users", users);
        return "reportDeveloper";
    }

}
