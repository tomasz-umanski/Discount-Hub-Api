package pl.tumanski.discounthub.postmanagement;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.tumanski.discounthub.categorymanagement.model.entity.Category;
import pl.tumanski.discounthub.postmanagement.model.dto.AuthorDto;
import pl.tumanski.discounthub.postmanagement.model.dto.CreatePostDto;
import pl.tumanski.discounthub.postmanagement.model.dto.PostCategoryDto;
import pl.tumanski.discounthub.postmanagement.model.dto.PostDto;
import pl.tumanski.discounthub.postmanagement.model.entity.Post;
import pl.tumanski.discounthub.usermanagement.model.entity.User;

@Mapper(componentModel = "spring")
interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    Post toPost(CreatePostDto createPostDto);

    PostDto toPostDto(Post post);

    PostCategoryDto toPostCategoryDto(Category category);

    AuthorDto toAuthorDto(User user);
}
