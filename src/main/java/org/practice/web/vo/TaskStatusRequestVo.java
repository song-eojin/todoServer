package org.practice.web.vo;

import lombok.Getter;
import lombok.ToString;
import org.practice.constants.TaskStatus;

@Getter
@ToString
public class TaskStatusRequestVo {
    private TaskStatus status;
}
