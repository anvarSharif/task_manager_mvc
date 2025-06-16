package com.example.task_manager_mvc.repo;

import com.example.task_manager_mvc.entity.User;
import com.example.task_manager_mvc.payload.CriminalUserDTO;
import com.example.task_manager_mvc.payload.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "SELECT * FROM user WHERE id IN (:selectedUserIds)", nativeQuery = true)
    List<User> getFilteredUser(List<UUID> selectedUserIds);
    @Query(value = """

            select u.full_name,
       u.photo_id,
       count(*) as overdue_task_count
from users u
         join
     public.task_users tu on u.id = tu.users_id
         join
     public.task t on t.id = tu.task_id
where t.deadline < current_date\s
group by u.id, u.full_name, u.photo_id
order by overdue_task_count desc
""", nativeQuery = true)

    List<CriminalUserDTO> getCriminalUser();

    /*@Query(value = """
           SELECT
               u.full_name,
               u.photo_id,
               COUNT(t.id) AS total_task_count,
               COUNT(CASE WHEN t.deadline < current_date THEN 1 END) AS overdue_task_count,
               COUNT(CASE WHEN t.deadline BETWEEN current_date AND current_date + INTERVAL '3 day' THEN 1 END) AS critical_task_count,
               COUNT(CASE WHEN t.deadline > current_date + INTERVAL '3 day' OR (t.deadline IS NULL and s.is_completed=FALSE)   THEN 1 END) AS inprog_task_count,
               COUNT(CASE WHEN s.is_completed = TRUE THEN 1 END) AS completed_task_count
           FROM
               users u
                   JOIN
               public.task_users tu ON u.id = tu.users_id
                   JOIN
               public.task t ON t.id = tu.task_id
                   JOIN
               public.status s ON t.status_id = s.id 
           GROUP BY
               u.id, u.full_name, u.photo_id
           ORDER BY
               overdue_task_count DESC;
           
""", nativeQuery = true)*/
    @Query(value = """
          SELECT
                                           u.full_name,
                                           u.photo_id,
                                           COUNT(t.id) AS total_task_count,
                                           COUNT(CASE WHEN t.deadline < CURRENT_DATE THEN 1 END) AS overdue_task_count,
                                           COUNT(CASE WHEN t.deadline BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '3 day' THEN 1 END) AS critical_task_count,
                                           COUNT(CASE WHEN t.deadline > CURRENT_DATE + INTERVAL '3 day' OR (t.deadline IS NULL AND s.is_completed = FALSE) THEN 1 END) AS inprog_task_count,
                                           COUNT(CASE WHEN s.is_completed = TRUE THEN 1 END) AS completed_task_count
                                       FROM
                                           users u
                                           JOIN public.task_users tu ON u.id = tu.users_id
                                           JOIN public.task t ON t.id = tu.task_id
                                           JOIN public.status s ON t.status_id = s.id
                                       GROUP BY
                                           u.id, u.full_name, u.photo_id
                                       ORDER BY
                                           overdue_task_count DESC;
                                       
           
""", nativeQuery = true)

    List<UserDTO> getResultTaskForUser();

    Optional<User> findByUsername(String username);
}