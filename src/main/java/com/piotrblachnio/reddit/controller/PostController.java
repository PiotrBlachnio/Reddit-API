package com.piotrblachnio.reddit.controller;

import com.piotrblachnio.reddit.dto.PostRequest;
import com.piotrblachnio.reddit.dto.PostResponse;
import com.piotrblachnio.reddit.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

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

    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/${id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }


    @GetMapping("/by-subreddit/${id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(Long id) {
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
    }

    @GetMapping("/by-user/${name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }
}
