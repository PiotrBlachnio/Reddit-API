package com.piotrblachnio.reddit.controllers;

import com.piotrblachnio.reddit.constants.ApiRoutes;
import com.piotrblachnio.reddit.dto.request.PostRequest;
import com.piotrblachnio.reddit.dto.response.PostResponse;
import com.piotrblachnio.reddit.services.PostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping(ApiRoutes.Post.CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody PostRequest postRequest) {
        postService.save(postRequest);
    }

    @GetMapping(ApiRoutes.Post.GET_ALL)
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getAll() {
        return postService.getAllPosts();
    }

    @GetMapping(ApiRoutes.Post.GET_BY_ID)
    @ResponseStatus(HttpStatus.OK)
    public PostResponse getById(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping(ApiRoutes.Post.GET_BY_SUBREDDIT)
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getBySubreddit(@PathVariable Long id) {
        return postService.getPostsBySubreddit(id);
    }

    @GetMapping(ApiRoutes.Post.GET_BY_USER)
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getByUser(@PathVariable String username) {
        return postService.getPostsByUsername(username);
    }
}