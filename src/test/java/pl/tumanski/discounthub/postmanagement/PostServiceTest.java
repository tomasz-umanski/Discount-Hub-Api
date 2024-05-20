package pl.tumanski.discounthub.postmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tumanski.discounthub.categorymanagement.CategoryService;
import pl.tumanski.discounthub.categorymanagement.model.dto.CreateCategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.entity.Category;
import pl.tumanski.discounthub.postmanagement.exception.PostNotFoundException;
import pl.tumanski.discounthub.postmanagement.model.dto.CreatePostDto;
import pl.tumanski.discounthub.postmanagement.model.dto.PatchPostDto;
import pl.tumanski.discounthub.postmanagement.model.entity.Post;
import pl.tumanski.discounthub.usermanagement.UserService;
import pl.tumanski.discounthub.usermanagement.model.dto.CreateUserDto;
import pl.tumanski.discounthub.usermanagement.model.entity.User;
import pl.tumanski.discounthub.utils.UnitTest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @Test
    void shouldCreateNewPostSuccessfully() {
        final Post post = createPost();
        assertTrue(Objects.nonNull(post) && Objects.nonNull(post.getId()));
    }

    @Test
    void shouldGetPostById() {
        Post post = createPost();
        Post retrievedPost = postService.getPostById(post.getId());
        assertTrue(Objects.nonNull(retrievedPost) && Objects.nonNull(retrievedPost.getId()));
    }

    @Test
    void shouldThrowPostNotFoundExceptionWhenThereIsNoPostWithProvidedId() {
        assertThrows(PostNotFoundException.class, () -> postService.getPostById(1L));
    }

    @Test
    void shouldGetAllCategories() {
        for (int i = 0; i < 10; i++) {
            createPost();
        }
        List<Post> postsRetrieved = postService.getAllPosts();
        assertTrue(Objects.nonNull(postsRetrieved) && !postsRetrieved.isEmpty());
    }

    @Test
    void shouldUpdatePostSuccessfully() {
        Post post = createPost();
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        PatchPostDto patchPostDto = PatchPostDto.builder()
                .categoryId(1L)
                .title("Updated Title")
                .description("Updated Description")
                .newPrice(BigDecimal.valueOf(100))
                .oldPrice(BigDecimal.valueOf(200))
                .deliveryPrice(BigDecimal.valueOf(20))
                .offerUrl("Updated URL")
                .imageUrl("Updated Image")
                .endDate(offsetDateTime)
                .build();

        Post updatedPost = postService.patchPost(post.getId(), patchPostDto);

        assertTrue(Objects.nonNull(updatedPost));
        assertEquals(1L, updatedPost.getCategory().getId());
        assertEquals("Updated Title", updatedPost.getTitle());
        assertEquals("Updated Description", updatedPost.getDescription());
        assertEquals(BigDecimal.valueOf(100), updatedPost.getNewPrice());
        assertEquals(BigDecimal.valueOf(200), updatedPost.getOldPrice());
        assertEquals(BigDecimal.valueOf(20), updatedPost.getDeliveryPrice());
        assertEquals("Updated URL", updatedPost.getOfferUrl());
        assertEquals("Updated Image", updatedPost.getImageUrl());
        assertEquals(offsetDateTime, updatedPost.getEndDate());
        assertEquals(post.getAuthor().getId(), updatedPost.getAuthor().getId());
    }

    @Test
    void shouldDeletePost() {
        Post post = createPost();
        postService.deletePost(post.getId());
        assertThrows(PostNotFoundException.class, () -> postService.getPostById(post.getId()));
    }

    @Test
    void shouldThrowCategoryNotFoundExceptionWhenThereIsNoCategoryToDeleteWithProvidedId() {
        assertThrows(PostNotFoundException.class, () -> postService.deletePost(1L));
    }

    private Post createPost() {
        User user = createUser();
        Category category = createCategory();

        CreatePostDto createPostDto = CreatePostDto.builder()
                .userId(user.getId())
                .categoryId(category.getId())
                .title("Great Discount on Laptops")
                .description("Huge discount on the latest laptops...")
                .newPrice(BigDecimal.valueOf(499))
                .oldPrice(BigDecimal.valueOf(999))
                .deliveryPrice(BigDecimal.valueOf(10))
                .offerUrl("https://example.com/offer")
                .imageUrl("https://example.com/image.jpg")
                .build();
        return postService.create(createPostDto);
    }

    private User createUser() {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .username(String.valueOf(UUID.randomUUID()))
                .email(String.valueOf(UUID.randomUUID()))
                .password("strongPassword")
                .build();
        return userService.registerUser(createUserDto);
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