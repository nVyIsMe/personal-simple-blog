package com.example.blog.service;

import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.model.Post;
import com.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

    public Post createPost(Post post) {
        // Lưu ý: ID sẽ tự sinh, createdAt tự sinh
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = getPostById(id);

        // Sử dụng Setter tường minh thay vì @Data
        post.setTitle(postDetails.getTitle());
        post.setDescription(postDetails.getDescription());
        post.setContent(postDetails.getContent());

        // createdAt không update, updatedAt tự động update nhờ annotation @UpdateTimestamp

        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }
}