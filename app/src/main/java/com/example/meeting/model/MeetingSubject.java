package com.example.meeting.model;

import androidx.annotation.NonNull;
public enum MeetingSubject {
    Mario("Mario"),
    Luigi("Luigi"),
    Peach("Peach");

    private String subject;

    MeetingSubject(String subject){
        this.subject=subject;
    }

    @NonNull
    @Override
    public String toString() {
        return subject;
    }
}
