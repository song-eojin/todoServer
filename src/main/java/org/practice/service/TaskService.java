package org.practice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.practice.constants.TaskStatus;
import org.practice.model.TaskDto;
import org.practice.persist.TaskRepository;
import org.practice.persist.entity.TaskEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<TaskDto> getAll() {
        return this.taskRepository.findAll().stream()
                .map(this::entityToObject)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getByDueDate(String dueDate) {
        return this.taskRepository.findAllByDueDate(Date.valueOf(dueDate)).stream()
                .map(this::entityToObject)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getByStatus(TaskStatus status) {
        return this.taskRepository.findAllByStatus(status).stream()
                .map(this::entityToObject)
                .collect(Collectors.toList());
    }

    public TaskDto getOne(Long id) {
        var entity = this.getById(id);
        return this.entityToObject(entity);
    }

    public boolean delete(Long id) {
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            log.error("an error occurred while deleting [{}]", e.toString());
            return false;
        }
        return true;
    }

    public TaskDto update(Long id, String title, String description, LocalDate dueDate) {
        var exists = this.getById(id);

        exists.setTitle(Strings.isEmpty(title) ?
                exists.getTitle() : title);
        exists.setDescription(Strings.isEmpty(description) ?
                exists.getDescription() : description);

        var updated = this.taskRepository.save(exists);
        return this.entityToObject(updated);
    }

    public TaskDto updateStatus(Long id, TaskStatus status) {
        var entity = this.getById(id);
        entity.setStatus(status);

        var saved = this.taskRepository.save(entity);
        return this.entityToObject(saved);
    }

    private TaskEntity getById(Long id) {
        return this.taskRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("not exists task id [%d]", id)));
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
