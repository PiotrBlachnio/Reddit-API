package com.piotrblachnio.reddit.controller;

import com.piotrblachnio.reddit.constants.ApiRoutes;
import com.piotrblachnio.reddit.dto.VoteDto;
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
    public ResponseEntity add(@RequestBody VoteDto voteDto) {
        voteService.vote(voteDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}