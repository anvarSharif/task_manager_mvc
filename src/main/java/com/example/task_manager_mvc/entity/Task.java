package com.example.task_manager_mvc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String description;
    @ManyToOne
    private Status status;
    @ManyToOne
    private Attachment file;
    @ManyToMany
    private List<User> users;
    private LocalDate deadline;
    //private String deadlineStatus;
    public Task(String title, String description, Status status, Attachment file, List<User> users, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.file = file;
        this.users = users;
        this.deadline = deadline;
    }

}
