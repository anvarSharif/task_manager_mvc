package com.example.task_manager_mvc.repo;

import com.example.task_manager_mvc.entity.Comment;
import com.example.task_manager_mvc.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByTask(Task task);
}