package com.piotrblachnio.reddit.service;

import com.piotrblachnio.reddit.dto.CommentDto;
import com.piotrblachnio.reddit.exceptions.PostNotFoundException;
import com.piotrblachnio.reddit.mapper.CommentMapper;
import com.piotrblachnio.reddit.model.NotificationEmail;
import com.piotrblachnio.reddit.model.User;
import com.piotrblachnio.reddit.repository.CommentRepository;
import com.piotrblachnio.reddit.repository.PostRepository;
import com.piotrblachnio.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void save(CommentDto commentDto) {
        var post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));

        var comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        var message = mailContentBuilder.build(post.getUser().getUsername() + "posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }
}
