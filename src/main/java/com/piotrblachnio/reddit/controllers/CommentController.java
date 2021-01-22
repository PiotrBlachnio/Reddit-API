package com.piotrblachnio.reddit.controllers;

import com.piotrblachnio.reddit.constants.ApiRoutes;
import com.piotrblachnio.reddit.dto.request.CommentRequest;
import com.piotrblachnio.reddit.dto.response.CommentResponse;
import com.piotrblachnio.reddit.services.CommentService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping(ApiRoutes.Comment.CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CommentRequest commentRequest) {
        commentService.save(commentRequest);
    }

    @GetMapping(ApiRoutes.Comment.GET_BY_POST)
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getByPost(@PathVariable Long id) {
        return commentService.getAllCommentsForPost(id);
    }

    @GetMapping(ApiRoutes.Comment.GET_BY_USER)
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getByUser(@PathVariable String username) {
        return commentService.getAllCommentsForUser(username);
    }
}