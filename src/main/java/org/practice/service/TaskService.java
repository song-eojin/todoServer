package org.practice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.practice.constants.TaskStatus;
import org.practice.model.TaskDto;
import org.practice.persist.TaskRepository;
import org.practice.persist.entity.TaskEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskDto add(String title, String description, LocalDate dueDate) {
        var e = TaskEntity.builder()
                .title(title)
                .description(description)
                .dueDate(Date.valueOf(dueDate))
                .status(TaskStatus.TODO)
                .build();
        var saved = this.taskRepository.save(e);
        return entityToObject(saved);
    }

    private TaskDto entityToObject(TaskEntity entity) {
        return TaskDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .dueDate(entity.getDueDate().toString())
                .createdAt(entity.getCreatedAt().toLocalDateTime())
                .updatedAt(entity.getUpdatedAt().toLocalDateTime())
                .build();
    }
}
