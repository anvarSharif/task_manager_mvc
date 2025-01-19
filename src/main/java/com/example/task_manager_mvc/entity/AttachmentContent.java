package com.example.task_manager_mvc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AttachmentContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @OneToOne
    private Attachment attachment;
    private byte[] bytes;

    public AttachmentContent(Attachment attachment, byte[] bytes) {
        this.attachment = attachment;
        this.bytes = bytes;
    }
}
