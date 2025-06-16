package com.example.task_manager_mvc.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {
    private UUID statusId;
    @NotNull(message = "status nomi bo'sh bo'la olmaydi")
    @NotEmpty(message = "status nomi bo'sh bo'la olmaydi")
    private String status;
    @NotNull(message = "tartib raqami bo'sh bo'la olmaydi")
    private Integer number;
    private Boolean isCompleted=false;
}
