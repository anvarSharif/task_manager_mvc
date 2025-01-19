package com.example.task_manager_mvc.repo;

import com.example.task_manager_mvc.entity.Attachment;
import com.example.task_manager_mvc.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {
    AttachmentContent findByAttachment(Attachment attachment);
}