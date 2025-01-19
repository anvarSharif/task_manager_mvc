package com.example.task_manager_mvc.controller;

import com.example.task_manager_mvc.entity.Comment;
import com.example.task_manager_mvc.entity.Status;
import com.example.task_manager_mvc.entity.Task;
import com.example.task_manager_mvc.payload.TaskFilter;
import com.example.task_manager_mvc.repo.CommentRepository;
import com.example.task_manager_mvc.repo.StatusRepository;
import com.example.task_manager_mvc.repo.TaskRepository;
import com.example.task_manager_mvc.repo.UserRepository;
import com.example.task_manager_mvc.specification.TaskFilterSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PageController {
    /*1-validationni qo'yib chiqish. statusni o'chirib bo'lmasligiga,loginga, task va statusni edite va saviga
     * 2- security qo'shish
     * 3-tilni to'g'irlab chiqish
     * 4-hamma pagega exit button qo'yib chiqish*/

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;
    private final CommentRepository commentRepository;

    @GetMapping
    public String getHome(Model model, @ModelAttribute TaskFilter taskFilter) {
        System.out.println(taskFilter);
        if (taskFilter.getUserId()==null) {
            taskFilter.setUserId(taskFilter.getSelectedUserId());
        }
        if (taskFilter.getUserId()!=null&&taskFilter.getUserId().equals(UUID.fromString("99bf70ac-c43b-47fa-83bb-ac38c0c72ae4"))){
            taskFilter.setUserId(null);
        }
        System.out.println("2-"+taskFilter);
        Specification<Task> spec = TaskFilterSpecification.taskSpecification(taskFilter);
        model.addAttribute("tasks", taskRepository.findAll(spec));
        model.addAttribute("users", userRepository.findAll());
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
    public String getAddStatus(Model model) {
        Optional<Status> byIsCompleted = statusRepository.findByIsCompletedTrue();
        if (!byIsCompleted.isEmpty()) {
            model.addAttribute("completedStatus", byIsCompleted.get());
        }
        return "addStatus";
    }

    @GetMapping("/status/edite")
    public String getEditeStatus(Model model, @RequestParam(required = false) UUID statusId) {

        model.addAttribute("endStatusOrder", statusRepository.getEndStatusOrder());
        Optional<Status> byIsCompleted = statusRepository.findByIsCompletedTrue();
        if (statusId != null) {
            Status status = statusRepository.findById(statusId).orElseThrow();
            model.addAttribute("status", status);
        }
        if (!byIsCompleted.isEmpty()) {
            model.addAttribute("completedStatus", byIsCompleted.get());
        }
        return "addStatus";
    }


    @GetMapping("/task/create")
    public String getAddTask(Model model, @RequestParam(required = false) UUID statusId, @RequestParam(required = false) UUID taskId) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("statusId", statusId);
        return "addTask";
    }

    @GetMapping("/task/{taskId}")
    public String getTask(Model model, @PathVariable UUID taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        model.addAttribute("task", task);
        List<Comment> comments = commentRepository.findByTask(task);
        model.addAttribute("comments", comments);
        return "task";
    }

}
