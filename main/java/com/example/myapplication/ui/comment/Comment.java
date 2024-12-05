package com.example.myapplication.ui.comment;

public class   Comment {
    private String content;   // 留言內容
    private String timestamp; // 留言時間

    public Comment(String content, String timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
