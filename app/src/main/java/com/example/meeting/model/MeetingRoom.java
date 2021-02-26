package com.example.meeting.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Arrays;

public enum MeetingRoom {
    A("Reunion A"),
    B("Reunion B"),
    C("Reunion C");

    private String room ;

    MeetingRoom(String room){
       this.room=room;
    }

    @NonNull
    @Override
    public String toString() {
        return room;
    }

    public static String[] getRooms(){
        MeetingRoom[] meetingRoom = MeetingRoom.values();
        String[] rooms = new String[meetingRoom.length];
        for(int i=0;i<meetingRoom.length;i++){
            rooms[i]= String.valueOf(meetingRoom[i]);
        }
        return rooms;
    }

}
