package com.piotrblachnio.reddit.mapper;

import com.piotrblachnio.reddit.dto.SubredditDto;
import com.piotrblachnio.reddit.model.Post;
import com.piotrblachnio.reddit.model.Subreddit;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}
