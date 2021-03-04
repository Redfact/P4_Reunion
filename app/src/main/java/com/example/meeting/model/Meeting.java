package com.example.meeting.model;

import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Meeting {

    private List<String> participants;
    private Date date;
    private MeetingRoom room;
    private MeetingSubject subject;
    public Meeting(Date date,MeetingRoom room, MeetingSubject subject){
        this.date=date;
        this.room=room;
        this.subject=subject;
        participants= new ArrayList<String>();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public MeetingRoom getRoom() {
        return room;
    }

    public void setRoom(MeetingRoom room) {
        this.room = room;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public int getParticipantCount(){return participants.size();}

    public String getParticipantsToString(){
        if(participants.size()>0)
            return participants.toString().substring(1,participants.toString().length()-1);
        else
            return "Pas de participant";
    }

    public MeetingSubject getSubject() {
        return subject;
    }

    public void setSubject(MeetingSubject subject) {
        this.subject = subject;
    }

    public String getMeetingInfos(){
        SimpleDateFormat format = new SimpleDateFormat("HH'h'mm");
        return room.toString()+" - "+format.format(date)+" - "+subject.toString();
    }

}