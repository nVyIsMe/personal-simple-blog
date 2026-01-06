package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts") // Do đã config context-path, đường dẫn thực tế sẽ là: /api/v1/posts
public class PostController {

    @Autowired
    private PostService postService;

    // GET: Lấy danh sách
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // GET: Lấy chi tiết
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // POST: Tạo mới
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        return new ResponseEntity<>(postService.createPost(post), HttpStatus.CREATED);
    }

    // PUT: Cập nhật
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        return ResponseEntity.ok(postService.updatePost(id, postDetails));
    }

    // DELETE: Xóa
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully.");
    }
}