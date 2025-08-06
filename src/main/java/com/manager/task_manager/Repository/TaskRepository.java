package com.manager.task_manager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manager.task_manager.Model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // 🔍 Get all tasks for a specific user
    List<Task> findByUserId(Long id); // ✅ camelCase

    // 🔍 Filter tasks by category name for a user
    List<Task> findByUser_IdAndCategory_Name(Long id, String name);

}
