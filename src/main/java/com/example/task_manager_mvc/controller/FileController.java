package com.example.task_manager_mvc.controller;

import com.example.task_manager_mvc.entity.Attachment;
import com.example.task_manager_mvc.entity.AttachmentContent;
import com.example.task_manager_mvc.repo.AttachmentContentRepository;
import com.example.task_manager_mvc.repo.AttachmentRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    @GetMapping("/get/{attachmentId}")
    public void get(@PathVariable UUID attachmentId, HttpServletResponse response) throws IOException {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(() -> new RuntimeException("file not found"));
        AttachmentContent content = attachmentContentRepository.findByAttachment(attachment);
        response.getOutputStream().write(content.getBytes());

    }

}
