package org.practice.persist;

import org.practice.constants.TaskStatus;
import org.practice.persist.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findAllByDueDate(Date date);

    List<TaskEntity> findAllByStatus(TaskStatus status);
}
