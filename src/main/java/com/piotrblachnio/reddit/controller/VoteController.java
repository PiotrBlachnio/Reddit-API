package com.piotrblachnio.reddit.controller;

import com.piotrblachnio.reddit.constants.ApiRoutes;
import com.piotrblachnio.reddit.dto.request.VoteRequest;
import com.piotrblachnio.reddit.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping(ApiRoutes.Vote.ADD)
    public ResponseEntity add(@RequestBody VoteRequest voteRequest) {
        voteService.vote(voteRequest);
        return new ResponseEntity(HttpStatus.OK);
    }
}