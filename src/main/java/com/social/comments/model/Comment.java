package com.social.comments.model;

import com.social.model.ParentType;

public class Comment {

    private String id;
    private String userId;
    private String parentId;
    private String content;
    private CommentContentType contentType;

    private ParentType parentType;

    public Comment(String userId, String parentId, String content, CommentContentType contentType, ParentType parentType) {
        this.userId = userId;
        this.parentId = parentId;
        this.content = content;
        this.contentType = contentType;
        this.parentType = parentType;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getParentId() {
        return parentId;
    }

    public String getContent() {
        return content;
    }

    public CommentContentType getContentType() {
        return contentType;
    }

    public ParentType getParentType() {
        return parentType;
    }
}
