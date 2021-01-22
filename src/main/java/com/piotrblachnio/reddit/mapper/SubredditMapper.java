package com.piotrblachnio.reddit.mapper;

import com.piotrblachnio.reddit.dto.request.SubredditRequest;
import com.piotrblachnio.reddit.dto.response.SubredditResponse;
import com.piotrblachnio.reddit.models.Post;
import com.piotrblachnio.reddit.models.Subreddit;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditResponse mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditRequest subredditRequest);
}