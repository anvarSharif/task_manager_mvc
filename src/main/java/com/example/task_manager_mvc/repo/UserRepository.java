package com.example.task_manager_mvc.repo;

import com.example.task_manager_mvc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "SELECT * FROM user WHERE id IN (:selectedUserIds)", nativeQuery = true)
    List<User> getFilteredUser(List<UUID> selectedUserIds);
}