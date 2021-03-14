package com.example.meeting.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.meeting.model.Meeting;
import com.example.meeting.model.MeetingRoom;
import com.example.meeting.model.MeetingSubject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class MeetingGenerator {
    public static List<Meeting> meetingList = Arrays.asList(
            new Meeting(Calendar.getInstance(),MeetingRoom.A, MeetingSubject.Mario),
            new Meeting(Calendar.getInstance(),MeetingRoom.B, MeetingSubject.Luigi),
            new Meeting(Calendar.getInstance(),MeetingRoom.C, MeetingSubject.Peach),
            new Meeting(Calendar.getInstance(),MeetingRoom.A, MeetingSubject.Mario),
            new Meeting(Calendar.getInstance(),MeetingRoom.B, MeetingSubject.Luigi)
    );
    public static List<String> mails = Arrays.asList(
                "martin@gmail.com",
                "nicolas@gmail.com",
                "medhi@yahoo.fr"
    );

    public static List<Meeting> generateReunion(){
        List<Meeting> reunionslist =  new ArrayList<>(meetingList);
        for (int i = 0 ; i < meetingList.size() ; i++){
            Calendar date = Calendar.getInstance();
            date.add(Calendar.HOUR,i);
            reunionslist.get(i).setParticipants(new ArrayList<String>(mails));
            meetingList.get(i).setDate(date);
        }
        return reunionslist;
    }
}
