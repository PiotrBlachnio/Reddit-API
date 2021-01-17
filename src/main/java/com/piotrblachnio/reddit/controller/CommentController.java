package com.piotrblachnio.reddit.controller;

import com.piotrblachnio.reddit.constants.ApiRoutes;
import com.piotrblachnio.reddit.dto.request.CommentRequest;
import com.piotrblachnio.reddit.dto.response.CommentResponse;
import com.piotrblachnio.reddit.service.CommentService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import java.util.List;

@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping(ApiRoutes.Comment.CREATE)
    public ResponseEntity create(@RequestBody CommentRequest commentRequest) {
        commentService.save(commentRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(ApiRoutes.Comment.GET_BY_POST)
    public ResponseEntity<List<CommentResponse>> getByPost(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPost(id));
    }

    @GetMapping(ApiRoutes.Comment.GET_BY_USER)
    public ResponseEntity<List<CommentResponse>> getByUser(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForUser(username));
    }
}