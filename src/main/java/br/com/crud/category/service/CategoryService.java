package br.com.crud.category.service;

import java.util.List;

import br.com.crud.category.repository.CategoryRepository;
import br.com.crud.entities.Category;
import br.com.crud.exception.ItemNotFoundException;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(Category category) {
        categoryRepository.persist(category);
        return category;
    }

    public List<Category> findAll(Integer page, Integer pageSize) {
        return categoryRepository
                .findAll(Sort.by("name"))
                .page(page, pageSize)
                .list();
    }

    public Category findById(UUID id) {
        return categoryRepository.findByIdOptional(id).orElseThrow(ItemNotFoundException::new);
    }

    public Category update(UUID id, Category category) {
        Category currentCategory = this.findById(id);
        currentCategory.name = category.name;

        categoryRepository.persist(currentCategory);

        return currentCategory;
    }

    public void delete(UUID id) {
        this.findById(id);
        categoryRepository.deleteById(id);
    }

    public Long total() {
        return categoryRepository.count();
    }
}
