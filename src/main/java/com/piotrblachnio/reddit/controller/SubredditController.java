package com.piotrblachnio.reddit.controller;

import com.piotrblachnio.reddit.constants.ApiRoutes;
import com.piotrblachnio.reddit.dto.request.SubredditRequest;
import com.piotrblachnio.reddit.dto.response.SubredditResponse;
import com.piotrblachnio.reddit.service.SubredditService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import lombok.AllArgsConstructor;
import java.util.List;

@RestController
@AllArgsConstructor
public class SubredditController {
    private final SubredditService subredditService;

    @PostMapping(ApiRoutes.Subreddit.CREATE)
    public ResponseEntity<SubredditResponse> create(@RequestBody SubredditRequest subredditRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditRequest));
    }

    @GetMapping(ApiRoutes.Subreddit.GET_ALL)
    public ResponseEntity<List<SubredditResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAll());
    }

    @GetMapping(ApiRoutes.Subreddit.GET_BY_ID)
    public ResponseEntity<SubredditResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getSubreddit(id));
    }
}