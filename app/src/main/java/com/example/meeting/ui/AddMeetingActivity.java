package com.example.meeting.ui;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.meeting.R;
import com.example.meeting.databinding.ActivityAddMeetingBinding;
import com.example.meeting.di.DI;
import com.example.meeting.model.Meeting;
import com.example.meeting.model.MeetingRoom;
import com.example.meeting.model.MeetingSubject;
import com.example.meeting.service.MeetingApiService;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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

        binding.addMeetingButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // Set date informations
                Calendar dateCalend = Calendar.getInstance();
                dateCalend.set(binding.datePicker.getYear(),binding.datePicker.getMonth(),binding.datePicker.getDayOfMonth(),binding.hourPicker.getHour(),binding.hourPicker.getMinute());
                Log.i("date","New date : "+dateCalend.get(Calendar.DAY_OF_MONTH)+" "+dateCalend.get(Calendar.MONTH)+" "+dateCalend.get(Calendar.YEAR));

                Meeting createdMeeting = new Meeting(dateCalend,meetingRoom,meetingSubject);

                createdMeeting.setParticipants(participantsList);
                //Add created meeting
                if(meetingApiService.canCreateMeeting(createdMeeting)) {
                    meetingApiService.addMeeting(createdMeeting);
                    Toast.makeText(getApplicationContext(), "Vous avez crée une nouvelle réunion ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Heure ou date de la reunion indisponible !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Select spinner informations
        binding.spinnerRoom.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String roomSelected = parent.getItemAtPosition(position).toString();
                meetingRoom = MeetingRoom.values()[position];
            }
        });
        binding.spinnerSubject.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                meetingSubject = MeetingSubject.values()[position];
            }
        });

        binding.addParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = binding.inputParticipants.getText().toString();
                binding.inputParticipants.setText("");
                if(!input.isEmpty()) {
                    participantsList.add(input);
                    Log.i("add", participantsList.toString());
                    binding.listParticipants.setText(participantsToString.append("\n" + input));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Vous n'avez pas ajouté de participant", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public static void navigate(FragmentActivity activity){
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}