package com.example.task_manager_mvc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String status;
    private Integer number;
    private Boolean isCompleted;

    public Status(String status, Integer number, Boolean isCompleted) {
        this.status = status;
        this.number = number;
        this.isCompleted = isCompleted;
    }
}
