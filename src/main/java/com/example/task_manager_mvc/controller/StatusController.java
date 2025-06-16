package com.example.task_manager_mvc.controller;

import com.example.task_manager_mvc.entity.Status;
import com.example.task_manager_mvc.payload.StatusDTO;
import com.example.task_manager_mvc.repo.StatusRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String addStatus(@Valid @ModelAttribute StatusDTO statusDTO,BindingResult bindingResult, Model model,@RequestParam UUID selectedUserId){
        model.addAttribute("userId",selectedUserId);
        if (bindingResult.hasErrors()){
            model.addAttribute("endStatusOrder", statusRepository.getEndStatusOrder());
            model.addAttribute("statusDTO",statusDTO);
            Optional<Status> byIsCompleted = statusRepository.findByIsCompletedTrue();
            byIsCompleted.ifPresent(status -> model.addAttribute("completedStatus", status));
            model.addAttribute("selectedUserId",selectedUserId);
            Status status=new Status(
                    statusDTO.getStatusId(),
                    statusDTO.getStatus(),
                    statusDTO.getNumber(),
                    statusDTO.getIsCompleted()
            );
            model.addAttribute("status",status);
            return "addStatus";
        }
        Status status=new Status(
                statusDTO.getStatus(),
                statusDTO.getNumber(),
                statusDTO.getIsCompleted()
        );
        statusRepository.save(status);
        return "redirect:/?userId="+selectedUserId;
    }
    @PostMapping("/edite")
    public String editeStatus(@Valid @ModelAttribute StatusDTO statusDTO,BindingResult bindingResult, Model model,@RequestParam UUID selectedUserId){
        model.addAttribute("userId",selectedUserId);
        if (bindingResult.hasErrors()){
            model.addAttribute("endStatusOrder", statusRepository.getEndStatusOrder());
            model.addAttribute("statusDTO",statusDTO);
            Optional<Status> byIsCompleted = statusRepository.findByIsCompletedTrue();
            byIsCompleted.ifPresent(status -> model.addAttribute("completedStatus", status));
            model.addAttribute("selectedUserId",selectedUserId);
            Status status=new Status(
                    statusDTO.getStatusId(),
                    statusDTO.getStatus(),
                    statusDTO.getNumber(),
                    statusDTO.getIsCompleted()
            );
            model.addAttribute("status",status);
            return "addStatus";
        }
        Status status=new Status(
                statusDTO.getStatusId(),
                statusDTO.getStatus(),
                statusDTO.getNumber(),
                statusDTO.getIsCompleted()
        );
        statusRepository.save(status);
        return "redirect:/?userId="+selectedUserId;
    }
    @PostMapping("/delete")
    public String delete(@RequestParam UUID statusId,@RequestParam UUID selectedUserId) {
        statusRepository.deleteById(statusId);
        return "redirect:/?userId="+selectedUserId;
    }
}
