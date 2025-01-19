package com.example.task_manager_mvc.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {
    private UUID statusId;
    private String status;
    private Integer number;
    private Boolean isCompleted=false;
}
