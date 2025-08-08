package com.manager.task_manager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.task_manager.Model.Category;
import com.manager.task_manager.Model.Task;
import com.manager.task_manager.Model.User;
import com.manager.task_manager.Repository.CategoryRepository;
import com.manager.task_manager.Repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    // ✅ Add new task
    public Task addTask(Task task, User user, Long categoryId) {
        task.setUser(user);

        if (categoryId != null) {
            Category category = categoryRepo.findById(categoryId).orElse(null);
            task.setCategory(category);
        }

        return taskRepo.save(task);
    }

    // ✅ Get tasks for logged-in user
    public List<Task> getTasksByUser(User user) {
        return taskRepo.findByUser(user);
    }

    // ✅ Delete task by ID
    public void deleteTask(Long taskId) {
        taskRepo.deleteById(taskId);
    }

    // ✅ Safe Update: only update title, description, completed
    public Task updateTask(Task updatedTask) {
        Task existingTask = taskRepo.findById(updatedTask.getId())
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + updatedTask.getId()));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());

        // 👇 NOTE: Do NOT touch user or category fields here

        return taskRepo.save(existingTask);
    }

    // ✅ Get single task by ID
    public Task getTaskById(Long id) {
        return taskRepo.findById(id).orElse(null);
    }
}
