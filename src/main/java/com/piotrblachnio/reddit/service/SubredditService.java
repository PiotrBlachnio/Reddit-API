package com.piotrblachnio.reddit.service;

import com.piotrblachnio.reddit.dto.request.SubredditRequest;
import com.piotrblachnio.reddit.dto.response.SubredditResponse;
import com.piotrblachnio.reddit.exceptions.SpringRedditException;
import com.piotrblachnio.reddit.mapper.SubredditMapper;
import com.piotrblachnio.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditResponse save(SubredditRequest subredditRequest) {
        var save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditRequest));
        return subredditMapper.mapSubredditToDto(save);
    }

    @Transactional(readOnly = true)
    public List<SubredditResponse> getAll() {
        return subredditRepository.findAll()
                    .stream()
                    .map(subredditMapper::mapSubredditToDto)
                    .collect(toList());
    }

    public SubredditResponse getSubreddit(Long id) {
        var subreddit = subredditRepository.findById(id)
                    .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));

        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
