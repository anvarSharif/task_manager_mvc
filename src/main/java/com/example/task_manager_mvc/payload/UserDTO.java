package com.example.task_manager_mvc.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String full_name;
    private UUID photo_id;
    private Long total_task_count; //hammasi
    private Long overdue_task_count; //otip ketgan
    private Long critical_task_count;// xavfli
    private Long inprog_task_count;// uzoq
    private Long completed_task_count;//tugagan
}
