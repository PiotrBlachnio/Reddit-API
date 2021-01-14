package com.piotrblachnio.reddit.service;

import com.piotrblachnio.reddit.dto.SubredditDto;
import com.piotrblachnio.reddit.exceptions.SpringRedditException;
import com.piotrblachnio.reddit.mapper.SubredditMapper;
import com.piotrblachnio.reddit.model.Subreddit;
import com.piotrblachnio.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        var save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                    .stream()
                    .map(subredditMapper::mapSubredditToDto)
                    .collect(toList());
    }

    public SubredditDto getSubreddit(Long id) {
        var subreddit = subredditRepository.findById(id)
                    .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));

        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
