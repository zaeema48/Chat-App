package com.example.chatapp.Model;

public class Message {
   private String sender_id, message;
   private long time;

    public Message(String sender_id, String message, long time) {
        this.sender_id = sender_id;
        this.message = message;
        this.time = time;
    }

    public Message() {
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
