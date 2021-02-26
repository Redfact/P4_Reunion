package com.example.meeting.service;

import com.example.meeting.di.DI;
import com.example.meeting.model.Meeting;
import com.example.meeting.model.MeetingRoom;
import com.example.meeting.model.MeetingSubject;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Date;
import java.util.List;

public class MeetingApiServiceTest extends TestCase {
    private List<Meeting> meetingList;
    private MeetingApiService meetingApiService;
    private int meetingNumber;

    public void setUp() throws Exception {
        super.setUp();
        meetingApiService= (MeetingApiService) DI.getMeetingApiService();
        meetingNumber = meetingApiService.getMeetingList().size();
    }

    public void testGetMeetingList() {
        meetingList= meetingApiService.getMeetingList();
        assertEquals(meetingList.size(),meetingApiService.getMeetingList().size());

        assertEquals(meetingList.get(0),meetingApiService.getMeetingList().get(0));
        assertEquals(meetingList.get(1),meetingApiService.getMeetingList().get(1));
        assertEquals(meetingList.get(2),meetingApiService.getMeetingList().get(2));
        assertEquals(meetingList.get(3),meetingApiService.getMeetingList().get(3));
        assertEquals(meetingList.get(4),meetingApiService.getMeetingList().get(4));
        assertEquals(meetingList.get(5),meetingApiService.getMeetingList().get(5));

    }

    @Test
    public void testAddDeleteMeeting() {
        Meeting newMeeting = new Meeting(new Date(System.currentTimeMillis()+(3600000*3)), MeetingRoom.C, MeetingSubject.Luigi);
        meetingApiService.addMeeting(newMeeting);
        assertEquals(meetingNumber+1,meetingApiService.getMeetingList().size());

        meetingApiService.deleteMeeting(newMeeting);
        assertEquals(meetingNumber,meetingApiService.getMeetingList().size());

    }

    public void testGetMeetingByRoom() {
        MeetingRoom room = MeetingRoom.C;
        List<Meeting> meetingsByRoom = meetingApiService.getMeetingByRoom(room);

        for (Meeting meeting:meetingsByRoom){
            assertEquals(meeting.getRoom(),room);
        }
    }

    public void testGetMeetingByDate() {
        Date today = new Date(System.currentTimeMillis());
        List<Meeting> meetingsByDate = meetingApiService.getMeetingByDate(today);

        for (Meeting meeting:meetingsByDate){
            assertEquals(meeting.getDate().getDate(),today.getDate());
            assertEquals(meeting.getDate().getMonth(),today.getMonth());
        }
    }

}