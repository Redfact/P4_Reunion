package com.example.meeting.service;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.meeting.model.Meeting;
import com.example.meeting.model.MeetingRoom;

import java.util.ArrayList;
import java.util.Calendar;
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

    public int getMeetingNumber(){ return meetingList.size();}

    public boolean canCreateMeeting(Meeting meeting) {
        Calendar date = meeting.getDate();
        for (Meeting curMeeting : meetingList) {
            Calendar curDate = curMeeting.getDate();

            if(curDate.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                    curDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH) &&
                    curDate.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                    curDate.get(Calendar.HOUR) == date.get(Calendar.HOUR) &&
                    curDate.get(Calendar.MINUTE) == date.get(Calendar.MINUTE)

            ){
                return  false ;
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

    public List<Meeting> getMeetingByDate(Calendar date){
        List<Meeting> meetings = new ArrayList<Meeting>();
        for(Meeting meeting:meetingList){
            Calendar curDate = meeting.getDate();
            if( curDate.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                    curDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH) &&
                    curDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)) {
                meetings.add(meeting);
            }
        }
        return meetings;
    }

}
