package com.example.meeting.service;

import com.example.meeting.model.Meeting;
import com.example.meeting.model.MeetingRoom;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface MeetingService {

    void addMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    List<Meeting> getMeetingList();

    List<Meeting> getMeetingByRoom(MeetingRoom room);

    List<Meeting> getMeetingByDate(Calendar date) throws ParseException;
}
