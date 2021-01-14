package com.piotrblachnio.reddit.mapper;

import com.piotrblachnio.reddit.dto.PostRequest;
import com.piotrblachnio.reddit.dto.PostResponse;
import com.piotrblachnio.reddit.model.Post;
import com.piotrblachnio.reddit.model.Subreddit;
import com.piotrblachnio.reddit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapToDto(Post post);
}
