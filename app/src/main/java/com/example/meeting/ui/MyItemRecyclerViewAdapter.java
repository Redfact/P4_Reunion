package com.example.meeting.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meeting.R;
import com.example.meeting.events.DeleteMeetingEvent;
import com.example.meeting.model.Meeting;
import com.mikhaellopez.circleview.CircleView;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
    private final List<Meeting> meetingList;

    public MyItemRecyclerViewAdapter(List<Meeting> items) {
        meetingList = items;
    }

    @Override
    public MyItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false);
        return new MyItemRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyItemRecyclerViewAdapter.ViewHolder holder, int position) {
        Meeting meeting = meetingList.get(position);
        holder.updateMeetingData(meeting);

        holder.deleteReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("del","Click on del button");
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.meetingInfos)
        TextView meetingInfos;
        @BindView(R.id.participants)
        TextView participants;
        @BindView(R.id.deleteButton)
        ImageButton deleteReunion;
        @BindView(R.id.circleView)
        CircleView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public  void updateMeetingData(Meeting meeting){
            int r= 10*meeting.getDate().get(Calendar.HOUR);
            int g= 255;
            int b=10*meeting.getDate().get(Calendar.MINUTE);
            meetingInfos.setText(meeting.getMeetingInfos());
            participants.setText(meeting.getParticipantsToString());
            imageView.setCircleColor(Color.rgb(r,g,b));
        }
    }

}