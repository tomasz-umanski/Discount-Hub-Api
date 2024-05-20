package pl.tumanski.discounthub.categorymanagement;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.tumanski.discounthub.categorymanagement.model.dto.CategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.dto.CreateCategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.entity.Category;

@Mapper(componentModel = "spring")
interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CreateCategoryDto createCategoryDto);

    CategoryDto toCategoryDto(Category category);

}
