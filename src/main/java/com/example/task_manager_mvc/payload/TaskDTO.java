package com.example.task_manager_mvc.payload;

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
    private String title;
    private String description;
    private List<UUID> selectedUsers;
    private LocalDate deadline;

}
