package com.zhans.post.model;

public enum PostStatus {
    ACTIVE("active"),
    WAITING("waiting"),
    FAILED("failed");

    private final String status;

    PostStatus(String status) {
        this.status = status;
    }

    public static PostStatus fromString(String status) {
        for (PostStatus postStatus : PostStatus.values()) {
            if (postStatus.status.equalsIgnoreCase(status)) {
                return postStatus;
            }
        }
        return null;
    }
}
