package com.manager.task_manager.Services;

import java.util.List;
import com.manager.task_manager.Model.Category; // ✅ Your custom entity

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.task_manager.Model.Task;
import com.manager.task_manager.Repository.CategoryRepository;
import com.manager.task_manager.Repository.TaskRepository;

@Service
public class TaskServices {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    
    //* Get All Task */
    public List<Task> getAllTask() {
        return (List<Task>) this.taskRepository.findAll();
    }

    //* Get Single Task */
    public Task getTask(Long tId) {
        return taskRepository.findById(tId).orElse(null);

    }

    //* Add Task */
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    //* Update Task */
    public Task updateTask(Task task, Long uId) {
        task.setTaskId(uId);
        return taskRepository.save(task);
    }

    //* Delete Task */
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    
    //* Get Task By User */
    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findByUserId(userId);
    }
    
    //* Get Task By User and Category */
    public List<Task> getTasksByUserAndCategory(Long userId, String name) {
        return taskRepository.findByUser_IdAndCategory_Name(userId, name);
    }

    //* Add Task With Category */
    public Task addTaskWithCategory(Task task, String name) {
         Category category = categoryRepository.findByName(name)
                 .orElseThrow(() -> new RuntimeException("Category not found"));
         task.setCategory(category);
         return taskRepository.save(task);
    }

    
}
