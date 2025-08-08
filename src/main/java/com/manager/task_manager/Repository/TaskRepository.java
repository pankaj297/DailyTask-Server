package com.manager.task_manager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manager.task_manager.Model.Task;
import com.manager.task_manager.Model.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}