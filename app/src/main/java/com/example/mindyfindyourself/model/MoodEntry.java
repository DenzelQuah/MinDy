package com.example.mindyfindyourself.model;

public class MoodEntry {
    private String id;
    private String mood;
    private long date;
    private String photoUri; // For camera photo
    private String location; // For GPS location

    public MoodEntry() {}

    public MoodEntry(String id, String mood, long date, String photoUri, String location) {
        this.id = id;
        this.mood = mood;
        this.date = date;
        this.photoUri = photoUri;
        this.location = location;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }
    public long getDate() { return date; }
    public void setDate(long date) { this.date = date; }
    public String getPhotoUri() { return photoUri; }
    public void setPhotoUri(String photoUri) { this.photoUri = photoUri; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
