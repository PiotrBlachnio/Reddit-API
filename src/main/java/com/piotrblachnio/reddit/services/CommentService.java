package com.piotrblachnio.reddit.services;

import com.piotrblachnio.reddit.dto.request.CommentRequest;
import com.piotrblachnio.reddit.dto.response.CommentResponse;
import com.piotrblachnio.reddit.exceptions.PostNotFoundException;
import com.piotrblachnio.reddit.exceptions.UsernameNotFoundException;
import com.piotrblachnio.reddit.mapper.CommentMapper;
import com.piotrblachnio.reddit.models.NotificationEmail;
import com.piotrblachnio.reddit.models.User;
import com.piotrblachnio.reddit.repositories.CommentRepository;
import com.piotrblachnio.reddit.repositories.PostRepository;
import com.piotrblachnio.reddit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentRequest commentRequest) {
        var post = postRepository.findById(commentRequest.getPostId()).orElseThrow(() -> new PostNotFoundException());

        var comment = commentMapper.map(commentRequest, post, authService.getCurrentUser());
        commentRepository.save(comment);

        var message = mailContentBuilder.build(post.getUser().getUsername() + "posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentResponse> getAllCommentsForPost(Long postId) {
        var post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());
        return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentResponse> getAllCommentsForUser(String userName) {
        var user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException());
        return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(toList());
    }
}