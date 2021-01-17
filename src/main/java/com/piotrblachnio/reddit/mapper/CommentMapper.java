package com.piotrblachnio.reddit.mapper;

import com.piotrblachnio.reddit.dto.request.CommentRequest;
import com.piotrblachnio.reddit.dto.response.CommentResponse;
import com.piotrblachnio.reddit.model.Comment;
import com.piotrblachnio.reddit.model.Post;
import com.piotrblachnio.reddit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentRequest.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentRequest commentRequest, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentResponse mapToDto(Comment comment);
}
