package com.example.mindyfindyourself.model;

public class JournalEntry {
    private String id;
    private String text;
    private long date;
    private String photoUri;
    private String location;

    public JournalEntry() {}

    public JournalEntry(String id, String text, long date, String photoUri, String location) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.photoUri = photoUri;
        this.location = location;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public long getDate() { return date; }
    public void setDate(long date) { this.date = date; }
    public String getPhotoUri() { return photoUri; }
    public void setPhotoUri(String photoUri) { this.photoUri = photoUri; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
