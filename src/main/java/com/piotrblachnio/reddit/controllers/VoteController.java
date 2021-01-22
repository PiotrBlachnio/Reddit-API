package com.piotrblachnio.reddit.controllers;

import com.piotrblachnio.reddit.constants.ApiRoutes;
import com.piotrblachnio.reddit.dto.request.VoteRequest;
import com.piotrblachnio.reddit.services.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping(ApiRoutes.Vote.ADD)
    @ResponseStatus(HttpStatus.OK)
    public void add(@Valid @RequestBody VoteRequest voteRequest) {
        voteService.vote(voteRequest);
    }
}