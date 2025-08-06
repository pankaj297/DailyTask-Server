package com.manager.task_manager.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    private String tasksName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-task")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference(value = "category-task")
    private Category category;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTasksName() {
        return tasksName;
    }

    public void setTasksName(String tasksName) {
        this.tasksName = tasksName;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Task(Long taskId, String tasksName, User user, Category category) {
        this.taskId = taskId;
        this.tasksName = tasksName;
        this.user = user;
        this.category = category;
    }

    public Task() {
    }

    @Override
    public String toString() {
        return "Task [taskId=" + taskId + ", tasksName=" + tasksName + ", user=" + user
                + ", category=" + category + "]";
    }



}
