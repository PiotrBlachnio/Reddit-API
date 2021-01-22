package com.piotrblachnio.reddit.services;

import com.piotrblachnio.reddit.dto.request.VoteRequest;
import com.piotrblachnio.reddit.exceptions.PostNotFoundException;
import com.piotrblachnio.reddit.exceptions.SpringRedditException;
import com.piotrblachnio.reddit.models.Post;
import com.piotrblachnio.reddit.models.Vote;
import com.piotrblachnio.reddit.repositories.PostRepository;
import com.piotrblachnio.reddit.repositories.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.piotrblachnio.reddit.models.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteRequest voteRequest) {
        var post = postRepository.findById(voteRequest.getPostId()).orElseThrow(() -> new PostNotFoundException());
        var voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteRequest.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteRequest.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteRequest.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteRequest, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteRequest voteRequest, Post post) {
        return Vote.builder()
                .voteType(voteRequest.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}