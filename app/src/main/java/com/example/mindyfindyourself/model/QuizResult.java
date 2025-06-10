package com.example.mindyfindyourself.model;

import java.io.Serializable;

public class QuizResult implements Serializable {
    private String id;
    private int score;
    private long date;
    private java.util.List<String> answers;

    public QuizResult() {}

    public QuizResult(String id, int score, long date, java.util.List<String> answers) {
        this.id = id;
        this.score = score;
        this.date = date;
        this.answers = answers;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public long getDate() { return date; }
    public void setDate(long date) { this.date = date; }
    public java.util.List<String> getAnswers() { return answers; }
    public void setAnswers(java.util.List<String> answers) { this.answers = answers; }
}
