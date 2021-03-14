package com.example.meeting.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.meeting.databinding.ActivityAddMeetingBinding;
import com.example.meeting.di.DI;
import com.example.meeting.model.Meeting;
import com.example.meeting.model.MeetingRoom;
import com.example.meeting.model.MeetingSubject;
import com.example.meeting.service.MeetingApiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class AddMeetingActivity extends BaseActivity<ActivityAddMeetingBinding>{
    private MeetingApiService meetingApiService;

    private List<String> meetingSubjectList = new LinkedList(Arrays.asList(MeetingSubject.values()));
    private List<String> meetingRoomList =  new LinkedList(Arrays.asList(MeetingRoom.values()));

    private MeetingRoom meetingRoom;
    private MeetingSubject meetingSubject;
    private List<String> participantsList = new ArrayList<String>();
    private StringBuilder participantsToString=new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meetingApiService = (MeetingApiService) DI.getMeetingApiService();
        meetingRoom= MeetingRoom.A;
        meetingSubject= MeetingSubject.Mario;
        binding.spinnerRoom.attachDataSource(meetingRoomList);
        binding.spinnerSubject.attachDataSource(meetingSubjectList);
        binding.hourPicker.setIs24HourView(true);
        binding.addMeetingButton.setOnClickListener(v -> {
            // Set date informations
            Calendar dateCalend = Calendar.getInstance();
            dateCalend.set(binding.datePicker.getYear(), binding.datePicker.getMonth(),binding.datePicker.getDayOfMonth(),binding.hourPicker.getHour(),binding.hourPicker.getMinute());
            Meeting createdMeeting = new Meeting(dateCalend,meetingRoom,meetingSubject);

            createdMeeting.setParticipants(participantsList);
            //Add created meeting
            if (meetingApiService.canCreateMeeting(createdMeeting)) {
                meetingApiService.addMeeting(createdMeeting);
                Toast.makeText(getApplicationContext(), "Vous avez crée une nouvelle réunion ", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Heure ou date de la reunion indisponible !", Toast.LENGTH_SHORT).show();
            }
        });

        //Select spinner informations
        binding.spinnerRoom.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
            String roomSelected = parent.getItemAtPosition(position).toString();
            meetingRoom = MeetingRoom.values()[position];
        });
        binding.spinnerSubject.setOnSpinnerItemSelectedListener((parent, view, position, id) -> meetingSubject = MeetingSubject.values()[position]);

        binding.addParticipants.setOnClickListener(v -> {
            String input = binding.inputParticipants.getText().toString();
            binding.inputParticipants.setText("");
            if(!input.isEmpty()) {
                participantsList.add(input);
                binding.listParticipants.setText(participantsToString.append("\n" + input));
            } else {
                Toast.makeText(getApplicationContext(), "Vous n'avez pas ajouté de participant", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void navigate(FragmentActivity activity){
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}