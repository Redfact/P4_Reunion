package com.example.meeting.model;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(JUnit4.class)
public class MeetingTest extends TestCase{
    Meeting meeting;
    List<String> mails = Arrays.asList("martin@gmail.com", "nicolas@gmail.com", "medhi@yahoo.fr");
    int timeMillis = (int) System.currentTimeMillis();
    @Before
    public void setUp() throws Exception {
        super.setUp();
        meeting =  new Meeting(Calendar.getInstance(), MeetingRoom.A, MeetingSubject.Mario) ;
    }

    @Test
    public void testSetGetParticipants() {
        meeting.setParticipants(mails);

        assertEquals(meeting.getParticipants().size(),mails.size());

        assertEquals(meeting.getParticipants().get(0) , mails.get(0) );
        assertEquals(meeting.getParticipants().get(1) , mails.get(1) );
        assertEquals(meeting.getParticipants().get(2) , mails.get(2) );
    }

    @Test
    public void testGetSetDate() {
        Calendar curDate = meeting.getDate();
        Calendar newDate = Calendar.getInstance();
        newDate.add(Calendar.MINUTE,5);
        assertEquals(curDate,Calendar.getInstance());

        meeting.setDate(newDate);
        assertEquals(newDate,meeting.getDate());
    }

    @Test
    public void testSetGetRoom() {
        MeetingRoom newRoom= MeetingRoom.B;
        assertEquals(meeting.getRoom(),MeetingRoom.A);

        meeting.setRoom(newRoom);
        assertEquals(meeting.getRoom(),MeetingRoom.B);
    }

    @Test
    public void testSetGetSubject() {
        MeetingSubject newSubject = MeetingSubject.Luigi;
        assertEquals(meeting.getSubject(),MeetingSubject.Mario);

        meeting.setSubject(newSubject);
        assertEquals(meeting.getSubject(),MeetingSubject.Luigi);
    }

}