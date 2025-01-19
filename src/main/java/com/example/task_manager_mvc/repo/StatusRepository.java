package com.example.task_manager_mvc.repo;

import com.example.task_manager_mvc.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {
    @Query(value = """
select max(number) from status
""",nativeQuery = true)
    Long getEndStatusOrder();
    @Query(value = """
            select * from status where is_completed=false order by number
            """,nativeQuery = true)
    List<Status> getFilteredStatuses();
    Optional<Status> findByIsCompletedTrue();
}