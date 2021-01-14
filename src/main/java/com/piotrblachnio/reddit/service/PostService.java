package com.piotrblachnio.reddit.service;

import com.piotrblachnio.reddit.dto.PostRequest;
import com.piotrblachnio.reddit.exceptions.SubredditNotFoundException;
import com.piotrblachnio.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
    private final SubredditRepository subredditRepository;
    private final AuthService authService;

    public void save(PostRequest postRequest) {
        subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()))
    }
}
