package com.piotrblachnio.reddit.dto.request;

import lombok.*;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditRequest {
    private Long id;

    private Integer numberOfPosts;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    private String description;
}