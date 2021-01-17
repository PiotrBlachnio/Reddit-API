package com.piotrblachnio.reddit.dto.response;

import lombok.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private Long postId;
    private Instant createdDate;
    private String text;
    private String userName;
}