package pl.tumanski.discounthub.postmanagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tumanski.discounthub.categorymanagement.CategoryService;
import pl.tumanski.discounthub.categorymanagement.model.entity.Category;
import pl.tumanski.discounthub.postmanagement.exception.PostCreationException;
import pl.tumanski.discounthub.postmanagement.exception.PostNotFoundException;
import pl.tumanski.discounthub.postmanagement.model.dto.CreatePostDto;
import pl.tumanski.discounthub.postmanagement.model.dto.PatchPostDto;
import pl.tumanski.discounthub.postmanagement.model.entity.Post;
import pl.tumanski.discounthub.usermanagement.UserService;
import pl.tumanski.discounthub.usermanagement.model.entity.User;

import java.util.ArrayList;
import java.util.List;

import static pl.tumanski.discounthub.utils.PatchUtils.shouldUpdateValue;

@Service
@Slf4j
class PostServiceImpl implements PostService {
    @Autowired
    private final PostRepository postRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public PostServiceImpl(PostRepository postRepository, UserService userService, CategoryService categoryService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public Post create(CreatePostDto createPostDto) {
        Post newPost = PostMapper.INSTANCE.toPost(createPostDto);
        User author = userService.getUserById(createPostDto.getUserId());
        Category category = categoryService.getCategoryById(createPostDto.getCategoryId());

        newPost.setAuthor(author);
        newPost.setCategory(category);

        try {
            Post createdPost = postRepository.save(newPost);
            log.info("Saved new post in repository with id = {}", createdPost.getId());
            return createdPost;
        } catch (Exception e) {
            log.error("Failed to save new post in repository", e);
            throw new PostCreationException("Failed to create new post", e);
        }
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
    }

    @Override
    public List<Post> getAllPosts() {
        return new ArrayList<>(postRepository.findAll());
    }

    @Override
    public Post patchPost(Long id, PatchPostDto patchPostDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        if (shouldUpdateValue(patchPostDto.getCategoryId())) {
            Category category = categoryService.getCategoryById(patchPostDto.getCategoryId());
            post.setCategory(category);
        }
        if (shouldUpdateValue(patchPostDto.getTitle())) {
            post.setTitle(patchPostDto.getTitle());
        }
        if (shouldUpdateValue(patchPostDto.getDescription())) {
            post.setDescription(patchPostDto.getDescription());
        }
        if (shouldUpdateValue(patchPostDto.getNewPrice())) {
            post.setNewPrice(patchPostDto.getNewPrice());
        }
        if (shouldUpdateValue(patchPostDto.getOldPrice())) {
            post.setOldPrice(patchPostDto.getOldPrice());
        }
        if (shouldUpdateValue(patchPostDto.getOldPrice())) {
            post.setOldPrice(patchPostDto.getOldPrice());
        }
        if (shouldUpdateValue(patchPostDto.getDeliveryPrice())) {
            post.setDeliveryPrice(patchPostDto.getDeliveryPrice());
        }
        if (shouldUpdateValue(patchPostDto.getOfferUrl())) {
            post.setOfferUrl(patchPostDto.getOfferUrl());
        }
        if (shouldUpdateValue(patchPostDto.getImageUrl())) {
            post.setImageUrl(patchPostDto.getImageUrl());
        }
        if (shouldUpdateValue(patchPostDto.getEndDate())) {
            post.setEndDate(patchPostDto.getEndDate());
        }

        try {
            Post updatedPost = postRepository.save(post);
            log.info("Patched post in repository with id = {}", updatedPost.getId());
            return updatedPost;
        } catch (Exception e) {
            log.error("Failed to patch post in repository", e);
            throw new PostCreationException("Failed to patch post", e);
        }
    }

    @Override
    public void deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            log.info("Deleted post with id = {}", id);
        } else {
            throw new PostNotFoundException("Post not found with id: " + id);
        }
    }
}
