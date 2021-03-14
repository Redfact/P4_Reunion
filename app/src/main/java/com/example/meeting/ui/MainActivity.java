package com.example.meeting.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.meeting.R;
import com.example.meeting.databinding.ActivityMainBinding;
import com.example.meeting.di.DI;
import com.example.meeting.events.DeleteMeetingEvent;
import com.example.meeting.events.FilterMeetingEvent;
import com.example.meeting.model.Meeting;
import com.example.meeting.model.MeetingRoom;
import com.example.meeting.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.List;
public class MainActivity extends BaseActivity<ActivityMainBinding>{
    private List<Meeting> meetingList;
    private MyItemRecyclerViewAdapter adapter;
    private MeetingApiService meetingApiService;
    private Calendar date;
    private MeetingRoom meetingRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = Calendar.getInstance();
        meetingApiService = (MeetingApiService) DI.getMeetingApiService();
        this.configureRecycleView();

        binding.addMeeting.setOnClickListener(new View.OnClickListener() {
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
        b.setItems(types, (dialog, which) -> {
            dialog.dismiss();
            meetingRoom= MeetingRoom.values()[which];
            EventBus.getDefault().post(new FilterMeetingEvent(meetingRoom));
        });
        b.show();
    }

    private void datePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            date.set(year, month, dayOfMonth);
            Log.i("date",date.get(Calendar.DAY_OF_MONTH)+" "+date.get(Calendar.MONTH)+" "+date.get(Calendar.YEAR));
            initListMeeting(2);
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
            this.meetingList = meetingApiService.getMeetingByDate(date);
        }

        this.adapter = new MyItemRecyclerViewAdapter(this.meetingList);
        binding.mainRecyclerView.setAdapter(this.adapter);
    }

    public void configureRecycleView(){
        initListMeeting(0);
        this.adapter = new MyItemRecyclerViewAdapter(this.meetingList);
        binding.mainRecyclerView.setAdapter(this.adapter);
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
            date = event.meetingDate;
            initListMeeting(2);
        } else {
            meetingRoom = event.meetingRoom;
            initListMeeting(1);
        }
    }

}