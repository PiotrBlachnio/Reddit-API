package com.piotrblachnio.reddit.services;

import com.piotrblachnio.reddit.dto.request.PostRequest;
import com.piotrblachnio.reddit.dto.response.PostResponse;
import com.piotrblachnio.reddit.exceptions.PostNotFoundException;
import com.piotrblachnio.reddit.exceptions.SubredditNotFoundException;
import com.piotrblachnio.reddit.exceptions.UsernameNotFoundException;
import com.piotrblachnio.reddit.mapper.PostMapper;
import com.piotrblachnio.reddit.repositories.PostRepository;
import com.piotrblachnio.reddit.repositories.SubredditRepository;
import com.piotrblachnio.reddit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void save(PostRequest postRequest) {
        var subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() -> new SubredditNotFoundException());

        var user = authService.getCurrentUser();

        postRepository.save(postMapper.map(postRequest, subreddit, user));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        var post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        var subreddit = subredditRepository.findById(subredditId).orElseThrow(() -> new SubredditNotFoundException());

        var posts = postRepository.findAllBySubreddit(subreddit);

        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException());
        return postRepository.findByUser(user) .stream().map(postMapper::mapToDto).collect(toList());
    }
}