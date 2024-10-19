package com.example.whatsapp.madels;

public class MessageModel {
    String userId;
    String message;
    String messageId;
    String senderName;
    boolean isMsgDelBySender = false;
    long timeStamp;

    public boolean isMsgDelBySender() {
        return isMsgDelBySender;
    }

    public void setMsgDelBySender(boolean msgDelBySender) {
        isMsgDelBySender = msgDelBySender;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public MessageModel(String userId, String message, String senderName) {
        this.userId = userId;
        this.message = message;
        this.senderName = senderName;
    }

    public MessageModel(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public MessageModel(String userId, String message, long timeStamp) {
        this.userId = userId;
        this.message = message;
        this.timeStamp = timeStamp;
    }
    public MessageModel(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
