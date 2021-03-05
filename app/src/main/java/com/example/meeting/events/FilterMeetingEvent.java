package com.example.meeting.events;

import com.example.meeting.model.Meeting;
import com.example.meeting.model.MeetingRoom;

import java.util.Calendar;
import java.util.Date;

public class FilterMeetingEvent {

    /**
     * Meeting to delete
     */
    public MeetingRoom meetingRoom;
//    public Date meetingDate;
    public Calendar meetingDate;

    /**
     * Constructor.
     * @param room
     */
    public FilterMeetingEvent(MeetingRoom room) {
        this.meetingRoom = room;
    }

    public FilterMeetingEvent(Calendar date){this.meetingDate=date; }
}
