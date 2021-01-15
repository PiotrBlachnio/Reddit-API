package com.piotrblachnio.reddit.controller;

import com.piotrblachnio.reddit.dto.CommentDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @PostMapping
    public void createComment(@RequestBody CommentDto commentDto) {

    }
}
