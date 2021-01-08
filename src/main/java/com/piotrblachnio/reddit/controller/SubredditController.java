package com.piotrblachnio.reddit.controller;

import com.piotrblachnio.reddit.dto.SubredditDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {
    public void createSubreddit(@RequestBody SubredditDto subredditDto) {

    }
}
