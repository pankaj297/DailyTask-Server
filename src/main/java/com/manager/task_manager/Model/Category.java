package com.manager.task_manager.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catId;

    private String name;

    @JsonManagedReference(value = "category-task")
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Task> task;

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }

    public Category(Long catId, String name, List<Task> task) {
        this.catId = catId;
        this.name = name;
        this.task = task;
    }

    public Category() {
    }

    @Override
    public String toString() {
        return "Category [catId=" + catId + ", name=" + name + ", task=" + task + "]";
    }


}

