package pl.tumanski.discounthub.categorymanagement;

import pl.tumanski.discounthub.categorymanagement.model.dto.CreateCategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.dto.PatchCategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.entity.Category;

import java.util.List;

public interface CategoryService {
    Category create(CreateCategoryDto createCategoryDto);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category patchCategory(Long id, PatchCategoryDto patchCategoryDto);

    void deleteCategory(Long id);
}
