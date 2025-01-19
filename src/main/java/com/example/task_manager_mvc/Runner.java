package com.example.task_manager_mvc;

import com.example.task_manager_mvc.entity.*;
import com.example.task_manager_mvc.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class Runner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;
    private final StatusRepository statusRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    public Runner(UserRepository userRepository,
                  AttachmentRepository attachmentRepository,
                  AttachmentContentRepository attachmentContentRepository,
                  StatusRepository statusRepository,
                  TaskRepository taskRepository,
                  CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
        this.statusRepository = statusRepository;
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {}/*{
        //attachment
        Attachment attachment=new Attachment(null,"rasm1");
        attachmentRepository.save(attachment);
        File file = new File("C:/java/PDP java/8-modul_spring-mvc/task_manager_mvc/src/main/java/com/example/task_manager_mvc/files/rasm1.jpg");
        byte[] bytes = Files.readAllBytes(file.toPath());
        AttachmentContent attachmentContent=new AttachmentContent(
                null,attachment,bytes
        );
        attachmentContentRepository.save(attachmentContent);

        Attachment attachment2=new Attachment(null,"rasm2");
        attachmentRepository.save(attachment2);
        File file2 = new File("C:/java/PDP java/8-modul_spring-mvc/task_manager_mvc/src/main/java/com/example/task_manager_mvc/files/rasm2.jpg");
        byte[] bytes2 = Files.readAllBytes(file2.toPath());
        AttachmentContent attachmentContent2=new AttachmentContent(
                null,attachment2,bytes2
        );
        attachmentContentRepository.save(attachmentContent2);

        //users
        User user1 = new User(
                null, "admin", "123", "Ilon Mask", attachment
        );
        User user2 = new User(
                null, "user", "123", "Eshmat toshmatov",null
        );
        User user3 = new User(
                null, "userjon", "123", "Hikmat Nursatov", null
        );
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

//task
        Status status=new Status(null,"OPEN",1,false);
        statusRepository.save(status);
        Status status2=new Status(null,"INPROGRES",2,false);
        statusRepository.save(status2);
        List<User> users=new ArrayList<>(List.of(
                user1
        ));
        List<User> users2=new ArrayList<>(List.of(
                user1,user2
        ));
        Task task=new Task(null,
                "task1","juda yaxshi task",status,attachment2,users, null);
        taskRepository.save(task);
        Task task2=new Task(null,
                "task2","juda yaxshi task",status,attachment2,users, null);
        taskRepository.save(task2);
        Task task3=new Task(null,
                "task3","juda yaxshi task",status,attachment2,users2, null);
        taskRepository.save(task3);

        //comment
        Comment comment=new Comment(null,"nima gap?",task,user1, LocalDateTime.now());
        commentRepository.save(comment);
    }*/
}
