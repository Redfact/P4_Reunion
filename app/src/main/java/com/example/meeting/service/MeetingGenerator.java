package com.example.meeting.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.meeting.model.Meeting;
import com.example.meeting.model.MeetingRoom;
import com.example.meeting.model.MeetingSubject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public abstract class MeetingGenerator {
    public static List<Meeting> meetingList = Arrays.asList(
            new Meeting(new Date(System.currentTimeMillis()), MeetingRoom.A, MeetingSubject.Mario),
            new Meeting(new Date(System.currentTimeMillis()+3600000),MeetingRoom.B,MeetingSubject.Luigi),
            new Meeting(new Date(System.currentTimeMillis()+(3600000*2)),MeetingRoom.C,MeetingSubject.Peach),
            new Meeting(new Date(System.currentTimeMillis()+(3600000*4)),MeetingRoom.C,MeetingSubject.Mario),
            new Meeting(new Date(System.currentTimeMillis()+(3600000*8)),MeetingRoom.A,MeetingSubject.Peach),
            new Meeting(new Date(System.currentTimeMillis()+(3600000*16)),MeetingRoom.B,MeetingSubject.Mario)
    );
    public static List<String> mails = Arrays.asList(
                "martin@gmail.com",
                "nicolas@gmail.com",
                "medhi@yahoo.fr"
    );

    public static List<Meeting> generateReunion(){
        List<Meeting> reunionslist =  new ArrayList<>(meetingList);
        for ( Meeting meeting : reunionslist){
            meeting.setParticipants(new ArrayList<String>(mails));
        }
        return reunionslist;
    }
}
