package com.piotrblachnio.reddit.controllers;

import com.piotrblachnio.reddit.constants.ApiRoutes;
import com.piotrblachnio.reddit.dto.request.SubredditRequest;
import com.piotrblachnio.reddit.dto.response.SubredditResponse;
import com.piotrblachnio.reddit.services.SubredditService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class SubredditController {
    private final SubredditService subredditService;

    @PostMapping(ApiRoutes.Subreddit.CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public SubredditResponse create(@Valid @RequestBody SubredditRequest subredditRequest) {
        return subredditService.save(subredditRequest);
    }

    @GetMapping(ApiRoutes.Subreddit.GET_ALL)
    @ResponseStatus(HttpStatus.OK)
    public List<SubredditResponse> getAll() {
        return subredditService.getAll();
    }

    @GetMapping(ApiRoutes.Subreddit.GET_BY_ID)
    @ResponseStatus(HttpStatus.OK)
    public SubredditResponse getById(@PathVariable Long id) {
        return subredditService.getSubreddit(id);
    }
}