package com.example.meeting.service;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.meeting.model.Meeting;
import com.example.meeting.model.MeetingRoom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MeetingApiService implements MeetingService {

    private List<Meeting> meetingList = MeetingGenerator.generateReunion();

    public void addMeeting(Meeting meeting){
        if( canCreateMeeting(meeting) ){
        meetingList.add(meeting);
        }
    }

    public void deleteMeeting(Meeting meeting){
        meetingList.remove(meeting);
    }

    public List<Meeting> getMeetingList() {
        return meetingList;
    }

    public boolean canCreateMeeting(Meeting meeting) {
        Date date = meeting.getDate();
        for (Meeting curMeeting : meetingList) {
            Date curDate = curMeeting.getDate();

            if( curDate.getHours()==date.getHours() &&
                    curDate.getMinutes()==date.getMinutes() &&
                    curDate.getDate()==date.getDate() &&
                    curDate.getMonth()==date.getMonth()

            ){
                return false ;
            }

        }
        return true ;
    }

    public List<Meeting> getMeetingByRoom(MeetingRoom room){
        List<Meeting> meetings = new ArrayList<Meeting>();
        for(Meeting meeting:meetingList){
            if(meeting.getRoom()==room){
                meetings.add(meeting);
            }
        }
        return meetings;
    }


    public List<Meeting> getMeetingByDate(Date date){
        List<Meeting> meetings = new ArrayList<Meeting>();
        for(Meeting meeting:meetingList){
                Date curDate = meeting.getDate();
            if(date.getDate()==curDate.getDate() && date.getMonth()==curDate.getMonth() ){
                meetings.add(meeting);
            }
        }
        return meetings;
    }

}
