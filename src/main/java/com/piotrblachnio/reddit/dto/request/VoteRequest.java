package com.piotrblachnio.reddit.dto.request;

import com.piotrblachnio.reddit.models.VoteType;
import lombok.*;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {
    @NotEmpty(message = "VoteType cannot be empty")
    private VoteType voteType;

    @NotEmpty(message = "PostId cannot be empty")
    private Long postId;
}