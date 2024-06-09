package com.jairo.dev.backend.service;

import com.jairo.dev.backend.model.Category;
import com.jairo.dev.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    public Category update(Category category) {
        return categoryRepository.save(category);
    }
}
