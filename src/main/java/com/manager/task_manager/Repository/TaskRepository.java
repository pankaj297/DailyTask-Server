package com.manager.task_manager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manager.task_manager.Model.Task;
import com.manager.task_manager.Model.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);

    List<Task> findByCategory_Name(String name);

    List<Task> findByUserAndCategory_Id(User user, Long categoryId);
}