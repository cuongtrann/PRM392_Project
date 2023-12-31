package com.example.project_prm392.models;

public class MessageModel {
    String uId, message, messageId;
    Long timestamp;

    public MessageModel(String uId, String message, Long timestamp) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public MessageModel(String uId, String message) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public MessageModel() {

    }


    public String getuId() {
        return uId;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageId() {
        return messageId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
