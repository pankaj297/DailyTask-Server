package com.manager.task_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manager.task_manager.Model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}