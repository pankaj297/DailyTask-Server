package com.manager.task_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manager.task_manager.Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}