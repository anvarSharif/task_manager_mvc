package com.example.task_manager_mvc.specification;


import com.example.task_manager_mvc.entity.Task;
import com.example.task_manager_mvc.payload.TaskFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;


public class TaskFilterSpecification {
    public static Specification<Task> taskSpecification(TaskFilter taskFilter) {
        return ((root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (taskFilter.getSearch() != null && !taskFilter.getSearch().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("title")), "%" + taskFilter.getSearch().toLowerCase() + "%"));
            }
            if (taskFilter.getUserId() != null) {
                predicate = cb.and(predicate, cb.equal(root.join("users").get("id"), taskFilter.getUserId()));
            }
            if (taskFilter.getDeadline_filter() != null) {
                switch (taskFilter.getDeadline_filter()) {
                    case "kechikkan":
                        predicate = cb.and(predicate, cb.lessThan(root.get("deadline"), LocalDate.now()));
                        break;
                    case "xavfli":
                        predicate = cb.and(predicate, cb.between(root.get("deadline"), LocalDate.now(), LocalDate.now().plusDays(1)));
                        break;
                    case "yaqin":
                        predicate = cb.and(predicate, cb.between(root.get("deadline"), LocalDate.now(), LocalDate.now().plusDays(3)));
                        break;
                    case "deadlinesiz":
                        break;
                    case "all":
                        break;
                    case "":
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid deadline filter: " + taskFilter.getDeadline_filter());
                }
            }
            return predicate;
        });
    }
}
