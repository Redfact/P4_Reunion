package com.example.meeting.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.meeting.R;
import com.example.meeting.databinding.ActivityMainBinding;
import com.example.meeting.di.DI;
import com.example.meeting.events.DeleteMeetingEvent;
import com.example.meeting.events.FilterMeetingEvent;
import com.example.meeting.model.Meeting;
import com.example.meeting.model.MeetingRoom;
import com.example.meeting.model.MeetingSubject;
import com.example.meeting.service.MeetingApiService;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Spinner;


import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends BaseActivity<ActivityMainBinding>{
    private List<Meeting> meetingList;
    private MyItemRecyclerViewAdapter adapter;
    private MeetingApiService meetingApiService;

    private Date dateSelected;

    private MeetingRoom meetingRoom;
    private List<String> meetingRoomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateSelected = new Date();
        meetingRoomList = new LinkedList(Arrays.asList(MeetingRoom.values()));
        meetingApiService = (MeetingApiService) DI.getMeetingApiService();
        this.configureRecycleView();

        binding.addReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddMeetingActivity.class);
                startActivity(intent, null);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.resetFilter) {
            initListMeeting(0);
        }
        if (id == R.id.roomFilter) {
            showDialogRoom();
        }
        if (id == R.id.dateFilter) {
            datePickerDialog();
        }

        return super.onOptionsItemSelected(item);
    }



    private void showDialogRoom(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Choisir une reunion : ");
        String[] types = MeetingRoom.getRooms();
        b.setItems(types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String meetingRoomName = types[which].toString();
                dialog.dismiss();
                meetingRoom= MeetingRoom.values()[which];
                EventBus.getDefault().post(new FilterMeetingEvent(meetingRoom));
            }
        });
        b.show();
    }

    private void datePickerDialog() {

//        Locale.setDefault(Locale.FRANCE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.i("filter",dayOfMonth + "-" + (month) + "-" + year);
                dateSelected.setYear(year);
                dateSelected.setMonth(month);
                dateSelected.setDate(dayOfMonth);
                initListMeeting(2);
            }
        },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListMeeting(0);
    }

    public void initListMeeting(int filter){
        if( filter == 0) {
            this.meetingList = meetingApiService.getMeetingList();
        }
        else if (filter == 1){
            this.meetingList = meetingApiService.getMeetingByRoom(meetingRoom);
        }
        else if (filter == 2){
            this.meetingList = meetingApiService.getMeetingByDate(dateSelected);
        }

        this.adapter = new MyItemRecyclerViewAdapter(this.meetingList);
        binding.mainRecyclerView.setAdapter(this.adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void configureRecycleView(){
        initListMeeting(0);
        this.adapter = new MyItemRecyclerViewAdapter(this.meetingList);
        binding.mainRecyclerView.setAdapter(this.adapter);
        //Position items
        binding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
            meetingApiService.deleteMeeting(event.meeting);
            initListMeeting(0);
    }

    @Subscribe
    public void onFilterMeeting(FilterMeetingEvent event){
        if(event.meetingDate!=null) {
            dateSelected = event.meetingDate;
            initListMeeting(2);
        }
        else {
            meetingRoom = event.meetingRoom;
            initListMeeting(1);
        }
    }

}