package com.piotrblachnio.reddit.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private Long id;

    private Instant createdDate;

    private String userName;

    @NotEmpty(message = "PostId cannot be empty")
    private Long postId;

    @NotEmpty(message = "Text cannot be empty")
    private String text;
}