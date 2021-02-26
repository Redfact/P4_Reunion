package com.example.meeting.events;

import com.example.meeting.model.Meeting;
import com.example.meeting.model.MeetingRoom;

import java.util.Date;

public class FilterMeetingEvent {

    /**
     * Meeting to delete
     */
    public MeetingRoom meetingRoom;
    public Date meetingDate;

    /**
     * Constructor.
     * @param room
     */
    public FilterMeetingEvent(MeetingRoom room) {
        this.meetingRoom = room;
    }

    public FilterMeetingEvent(Date date){this.meetingDate=date; }
}
