package com.example.task_manager_mvc.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private UUID statusId;
    private UUID taskId;
    @NotNull(message = "Task nomi bo'sh bo'la olmaydi")
    @NotEmpty(message = "Task nomi bo'sh bo'la olmaydi")
    private String title;
    private String description;
    private List<UUID> selectedUsers;
    private LocalDate deadline;

}
