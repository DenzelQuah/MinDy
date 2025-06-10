package com.example.mindyfindyourself.model;

public class UserProfile {
    private String id;
    private String name;
    private String avatarUri;

    public UserProfile() {}

    public UserProfile(String id, String name, String avatarUri) {
        this.id = id;
        this.name = name;
        this.avatarUri = avatarUri;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAvatarUri() { return avatarUri; }
    public void setAvatarUri(String avatarUri) { this.avatarUri = avatarUri; }
}
