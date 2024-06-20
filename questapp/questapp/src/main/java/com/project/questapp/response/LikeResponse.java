package com.project.questapp.response;

import com.project.questapp.entities.Like;
import lombok.Data;

@Data
public class LikeResponse {
    Long id;
    Long userId;
    Long postId;

    public LikeResponse(Like likeEntity) {
        this.id = likeEntity.getId();
        this.userId = likeEntity.getUser().getId();
        this.postId = likeEntity.getPost().getId();
    }
}
