package com.piotrblachnio.reddit.controller;

import com.piotrblachnio.reddit.constants.ApiRoutes;
import com.piotrblachnio.reddit.dto.SubredditDto;
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
    public ResponseEntity<SubredditDto> create(@RequestBody SubredditDto subredditDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto));
    }

    @GetMapping(ApiRoutes.Subreddit.GET_ALL)
    public ResponseEntity<List<SubredditDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAll());
    }

    @GetMapping(ApiRoutes.Subreddit.GET_BY_ID)
    public ResponseEntity<SubredditDto> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getSubreddit(id));
    }
}