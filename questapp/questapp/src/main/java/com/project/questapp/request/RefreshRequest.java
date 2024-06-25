package com.project.questapp.request;

import lombok.Data;

@Data
public class RefreshRequest {
    String refreshToken;
    Long userId;
}
