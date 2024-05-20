package pl.tumanski.discounthub.categorymanagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.tumanski.discounthub.categorymanagement.exception.CategoryCreationException;
import pl.tumanski.discounthub.categorymanagement.exception.CategoryNotFoundException;
import pl.tumanski.discounthub.categorymanagement.model.dto.CreateCategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.dto.PatchCategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.entity.Category;

import java.util.ArrayList;
import java.util.List;

import static pl.tumanski.discounthub.utils.PatchUtils.shouldUpdateValue;

@Service
@Slf4j
class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(CreateCategoryDto createCategoryDto) {
        Category newCategory = CategoryMapper.INSTANCE.toCategory(createCategoryDto);
        try {
            Category createdCategory = categoryRepository.save(newCategory);
            log.info("Saved new category in repository with id = {}", createdCategory.getId());
            return createdCategory;
        } catch (DataIntegrityViolationException e) {
            handleDataIntegrityViolation(e, newCategory);
        } catch (Exception e) {
            log.error("Failed to save new category in repository", e);
            throw new CategoryCreationException("Failed to create new category", e);
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(categoryRepository.findAll());
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
    }

    @Override
    public Category patchCategory(Long id, PatchCategoryDto patchCategoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        if (shouldUpdateValue(patchCategoryDto.getName())) {
            category.setName(patchCategoryDto.getName());
        }
        if (shouldUpdateValue(patchCategoryDto.getIcon())) {
            category.setIcon(patchCategoryDto.getIcon());
        }
        if (shouldUpdateValue(patchCategoryDto.getUrl())) {
            category.setUrl(patchCategoryDto.getUrl());
        }

        try {
            Category updatedCategory = categoryRepository.save(category);
            log.info("Patched category with id = {}", updatedCategory.getId());
            return updatedCategory;
        } catch (DataIntegrityViolationException e) {
            handleDataIntegrityViolation(e, category);
        } catch (Exception e) {
            log.error("Failed to patch category in repository", e);
            throw new CategoryCreationException("Failed to patch category", e);
        }
        return null;
    }

    @Override
    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            log.info("Deleted category with id = {}", id);
        } else {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
    }

    private void handleDataIntegrityViolation(DataIntegrityViolationException e, Category newCategory) {
        log.error("Failed to save new category due to data integrity violation", e);

        String causeMessage = (e.getCause() != null) ? e.getCause().getMessage() : "";

        if (causeMessage.contains("UNIQUE_NAME")) {
            throw new CategoryCreationException("Name already in use: " + newCategory.getName(), e);
        } else if (causeMessage.contains("UNIQUE_ICON")) {
            throw new CategoryCreationException("Icon already in use: " + newCategory.getIcon(), e);
        } else if (causeMessage.contains("UNIQUE_URL")) {
            throw new CategoryCreationException("Url already in use: " + newCategory.getUrl(), e);
        } else {
            throw new CategoryCreationException("Data integrity violation occurred", e);
        }
    }
}
