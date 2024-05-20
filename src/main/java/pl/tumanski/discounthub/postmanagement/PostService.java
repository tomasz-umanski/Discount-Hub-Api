package pl.tumanski.discounthub.postmanagement;

import pl.tumanski.discounthub.postmanagement.model.dto.CreatePostDto;
import pl.tumanski.discounthub.postmanagement.model.dto.PatchPostDto;
import pl.tumanski.discounthub.postmanagement.model.entity.Post;

import java.util.List;

public interface PostService {
    Post create(CreatePostDto createPostDto);

    Post getPostById(Long id);

    List<Post> getAllPosts();

    Post patchPost(Long id, PatchPostDto patchPostDto);

    void deletePost(Long id);
}
