package com.manager.task_manager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.task_manager.Model.Category;
import com.manager.task_manager.Repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	public List<Category> getAllCategories() {
		return categoryRepo.findAll();
	}

	public Category getCategoryById(Long id) {
		return categoryRepo.findById(id).orElse(null);
	}

	public Category addCategory(Category category) {
		return categoryRepo.save(category);
	}

	public void deleteCategory(Long id) {
		categoryRepo.deleteById(id);
	}
}