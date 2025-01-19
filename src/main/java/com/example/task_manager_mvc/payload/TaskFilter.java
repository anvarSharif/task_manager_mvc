package com.example.task_manager_mvc.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskFilter {
    private String search="";
    private UUID userId;
    private UUID selectedUserId;
    private String deadline_filter="";
}
