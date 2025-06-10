package com.example.mindyfindyourself.model;

public class Reminder {
    private String id;
    private String message;
    private long time;

    public Reminder() {}

    public Reminder(String id, String message, long time) {
        this.id = id;
        this.message = message;
        this.time = time;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public long getTime() { return time; }
    public void setTime(long time) { this.time = time; }
}
