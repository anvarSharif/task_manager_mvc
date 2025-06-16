package com.example.task_manager_mvc.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriminalUserDTO {
    private String full_name;
    private UUID photo_id;
    private Long overdue_task_count;
}
