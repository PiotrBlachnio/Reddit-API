package com.piotrblachnio.reddit.controller;

import com.piotrblachnio.reddit.dto.PostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/${id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping("/")
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/by-subreddit/${id}")
    public List<PostResponse> getPostsBySubreddit(Long id) {
        return postSerice.getPostsBySubreddit(id);
    }

    @GetMapping("/by-user/${name}")
    public List<PostResponse> getPostsByUsername(String username) {
        return postService.getPostsByUsername(username);
    }
}
