package com.example.task_manager_mvc.controller;

import com.example.task_manager_mvc.entity.Status;
import com.example.task_manager_mvc.payload.StatusDTO;
import com.example.task_manager_mvc.repo.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusRepository statusRepository;

    @PostMapping("/create")
    public String addStatus(@ModelAttribute StatusDTO statusDTO){
        Status status=new Status(
                statusDTO.getStatus(),
                statusDTO.getNumber(),
                statusDTO.getIsCompleted()
        );
        statusRepository.save(status);
        return "redirect:/";
    }
    @PostMapping("/edite")
    public String editeStatus(@ModelAttribute StatusDTO statusDTO){
        Status status=new Status(
                statusDTO.getStatusId(),
                statusDTO.getStatus(),
                statusDTO.getNumber(),
                statusDTO.getIsCompleted()
        );
        statusRepository.save(status);
        return "redirect:/";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam UUID statusId) {
        statusRepository.deleteById(statusId);
        return "redirect:/";
    }
}
