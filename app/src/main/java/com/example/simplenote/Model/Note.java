package com.example.simplenote.Model;

import androidx.annotation.NonNull;

public class Note {
    private int id;
    private String text;
    private String title;
    private  String time;
    public Note(String text,String time, String title) {
        this.text = text;
        this.title = title;
        this.time=time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Note(int id, String text, String time, String title) {
        this.id = id;
        this.text = text;
        this.time = time;
        this.title=title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return this.title.toString();
    }
}
