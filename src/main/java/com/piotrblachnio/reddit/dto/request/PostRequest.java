package com.piotrblachnio.reddit.dto.request;

import lombok.*;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long postId;

    @NotEmpty(message = "SubredditName cannot be empty")
    private String subredditName;

    @NotEmpty(message = "PostName cannot be empty")
    private String postName;

    @NotEmpty(message = "Url cannot be empty")
    private String url;

    @NotEmpty(message = "Description cannot be empty")
    private String description;
}
