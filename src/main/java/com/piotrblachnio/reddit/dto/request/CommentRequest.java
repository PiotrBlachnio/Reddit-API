package com.piotrblachnio.reddit.dto.request;

import lombok.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private Long id;
    private Long postId;
    private Instant createdDate;
    private String text;
    private String userName;
}