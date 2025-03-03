package org.practice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.practice.constants.TaskStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private String dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
