package pl.tumanski.discounthub.categorymanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tumanski.discounthub.categorymanagement.CategoryService;
import pl.tumanski.discounthub.categorymanagement.exception.CategoryCreationException;
import pl.tumanski.discounthub.categorymanagement.exception.CategoryNotFoundException;
import pl.tumanski.discounthub.categorymanagement.model.dto.CategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.dto.CreateCategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.dto.PatchCategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.entity.Category;
import pl.tumanski.discounthub.utils.UnitTest;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void shouldCreateNewCategorySuccessfully() {
        final Category category = createCategory();
        assertTrue(Objects.nonNull(category) && Objects.nonNull(category.getId()) && Objects.nonNull(category.getIcon()) && Objects.nonNull(category.getName()) && Objects.nonNull(category.getUrl()));
    }

    @Test
    void shouldThrowCategoryCreationExceptionWhenCategoryNameAlreadyInUse() {
        final String name = "name";
        final String icon = "icon";
        final String url = "url";
        final CreateCategoryDto createCategoryDto = CreateCategoryDto.builder()
                .name(name)
                .icon(icon)
                .url(url)
                .build();
        final CreateCategoryDto createCategoryDtoWithSameName = CreateCategoryDto.builder()
                .name(name)
                .icon(String.valueOf(UUID.randomUUID()))
                .url(String.valueOf(UUID.randomUUID()))
                .build();
        categoryService.create(createCategoryDto);
        assertThrows(CategoryCreationException.class, () -> categoryService.create(createCategoryDtoWithSameName));
    }

    @Test
    void shouldThrowCategoryCreationExceptionWhenCategoryIconAlreadyInUse() {
        final String name = "name";
        final String icon = "icon";
        final String url = "url";
        final CreateCategoryDto createCategoryDto = CreateCategoryDto.builder()
                .name(name)
                .icon(icon)
                .url(url)
                .build();
        final CreateCategoryDto createCategoryDtoWithSameName = CreateCategoryDto.builder()
                .name(String.valueOf(UUID.randomUUID()))
                .icon(icon)
                .url(String.valueOf(UUID.randomUUID()))
                .build();
        categoryService.create(createCategoryDto);
        assertThrows(CategoryCreationException.class, () -> categoryService.create(createCategoryDtoWithSameName));
    }

    @Test
    void shouldThrowCategoryCreationExceptionWhenCategoryUrlAlreadyInUse() {
        final String name = "name";
        final String icon = "icon";
        final String url = "url";
        final CreateCategoryDto createCategoryDto = CreateCategoryDto.builder()
                .name(name)
                .icon(icon)
                .url(url)
                .build();
        final CreateCategoryDto createCategoryDtoWithSameName = CreateCategoryDto.builder()
                .name(String.valueOf(UUID.randomUUID()))
                .icon(String.valueOf(UUID.randomUUID()))
                .url(url)
                .build();
        categoryService.create(createCategoryDto);
        assertThrows(CategoryCreationException.class, () -> categoryService.create(createCategoryDtoWithSameName));
    }

    @Test
    void shouldGetCategoryById() {
        Category category = createCategory();
        Category retrievedCategory = categoryService.getCategoryById(category.getId());
        assertTrue(Objects.nonNull(retrievedCategory) && Objects.nonNull(retrievedCategory.getId()));
    }

    @Test
    void shouldThrowCategoryNotFoundExceptionWhenThereIsNoCategoryWithProvidedId() {
        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    void shouldGetAllCategories() {
        for (int i = 0; i < 10; i++) {
            createCategory();
        }
        List<Category> categoriesRetrieved = categoryService.getAllCategories();
        assertTrue(Objects.nonNull(categoriesRetrieved) && !categoriesRetrieved.isEmpty());
    }

    @Test
    void shouldUpdateCategorySuccessfully() {
        Category category = createCategory();
        PatchCategoryDto patchCategoryDto = PatchCategoryDto.builder()
                .name("Updated Name")
                .icon("Updated Icon")
                .url("updated-url")
                .build();

        Category updatedCategory = categoryService.patchCategory(category.getId(), patchCategoryDto);

        assertTrue(Objects.nonNull(updatedCategory));
        assertEquals("Updated Name", updatedCategory.getName());
        assertEquals("Updated Icon", updatedCategory.getIcon());
        assertEquals("updated-url", updatedCategory.getUrl());
    }

    @Test
    void shouldNotUpdateWithBlankValues() {
        Category category = createCategory();
        String originalName = category.getName();
        String originalIcon = category.getIcon();
        String originalUrl = category.getUrl();

        PatchCategoryDto patchCategoryDto = PatchCategoryDto.builder()
                .name("")
                .icon("")
                .url("")
                .build();

        Category updatedCategory = categoryService.patchCategory(category.getId(), patchCategoryDto);

        assertTrue(Objects.nonNull(updatedCategory));
        assertEquals(updatedCategory.getName(), originalName);
        assertEquals(updatedCategory.getIcon(), originalIcon);
        assertEquals(updatedCategory.getUrl(), originalUrl);
    }

    @Test
    void shouldThrowCategoryNotFoundExceptionWhenUpdatingNonExistentCategory() {
        Long nonExistentId = 999L;
        PatchCategoryDto patchCategoryDto = PatchCategoryDto.builder()
                .name("Updated Name")
                .build();

        assertThrows(CategoryNotFoundException.class, () -> categoryService.patchCategory(nonExistentId, patchCategoryDto));
    }

    @Test
    void shouldThrowCategoryCreationExceptionWhenCategoryUrlAlreadyWhenUpdating() {
        Category category = createCategory();
        Category categoryToUpdate = createCategory();
        PatchCategoryDto patchCategoryDto = PatchCategoryDto.builder()
                .name(category.getName())
                .build();

        assertThrows(CategoryCreationException.class, () -> categoryService.patchCategory(categoryToUpdate.getId(), patchCategoryDto));
    }

    @Test
    void shouldDeleteCategory() {
        Category category = createCategory();
        categoryService.deleteCategory(category.getId());
        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById(category.getId()));
    }

    @Test
    void shouldThrowCategoryNotFoundExceptionWhenThereIsNoCategoryToDeleteWithProvidedId() {
        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory(1L));
    }

    private Category createCategory() {
        CreateCategoryDto createCategoryDto = CreateCategoryDto.builder()
                .name(String.valueOf(UUID.randomUUID()))
                .url(String.valueOf(UUID.randomUUID()))
                .icon(String.valueOf(UUID.randomUUID()))
                .build();
        return categoryService.create(createCategoryDto);
    }
}