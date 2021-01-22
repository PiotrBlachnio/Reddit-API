package com.piotrblachnio.reddit.controllers;

import com.piotrblachnio.reddit.constants.ApiRoutes;
import com.piotrblachnio.reddit.dto.request.PostRequest;
import com.piotrblachnio.reddit.dto.response.PostResponse;
import com.piotrblachnio.reddit.services.PostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import lombok.AllArgsConstructor;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping(ApiRoutes.Post.CREATE)
    public ResponseEntity create(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(ApiRoutes.Post.GET_ALL)
    public ResponseEntity<List<PostResponse>> getAll() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping(ApiRoutes.Post.GET_BY_ID)
    public ResponseEntity<PostResponse> getById(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping(ApiRoutes.Post.GET_BY_SUBREDDIT)
    public ResponseEntity<List<PostResponse>> getBySubreddit(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
    }

    @GetMapping(ApiRoutes.Post.GET_BY_USER)
    public ResponseEntity<List<PostResponse>> getByUser(@PathVariable String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }
}