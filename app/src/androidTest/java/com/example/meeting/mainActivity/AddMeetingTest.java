package com.example.meeting.mainActivity;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.rule.ActivityTestRule;

import com.example.meeting.R;
import com.example.meeting.di.DI;
import com.example.meeting.model.Meeting;
import com.example.meeting.service.MeetingApiService;
import com.example.meeting.ui.AddMeetingActivity;
import com.example.meeting.ui.MainActivity;
import com.example.meeting.utils.RecyclerViewItemCountAssertion;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasFocus;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.meeting.model.MeetingSubject.Peach;
import static java.util.EnumSet.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AddMeetingTest {

    private AddMeetingActivity mActivity;

    private int meetingNumber;

    private MeetingApiService meetingApiService= (MeetingApiService) DI.getMeetingApiService();

    @Rule
    public ActivityTestRule<AddMeetingActivity> mainActivityActivityTestRule = new ActivityTestRule(AddMeetingActivity.class);

    @Before
    public void setup(){
        mActivity = mainActivityActivityTestRule.getActivity();
        assertThat(mActivity,notNullValue());
        meetingNumber = meetingApiService.getMeetingNumber();
    }

    @Test
    public void testAddMeeting(){
        //select Peach
        onView(withId(R.id.spinnerSubject)).perform(click());
        onData(anything()).atPosition(1).perform(click());

        //select Reunion
        onView(withId(R.id.spinnerRoom)).perform(click());
        onData(anything()).atPosition(0).perform(click());

        //Add participants
        onView(withId(R.id.inputParticipants)).perform(typeText("test@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.addParticipants)).perform(click());

        onView(withId(R.id.hourPicker)).perform(PickerActions.setTime(1,1));
        onView(withId(R.id.datePicker)).perform(PickerActions.setDate(2021,5,5));

        //Scrool the page and click on "Ajouter" button
        onView(withId(R.id.addMeetingButton)).perform(scrollTo()).perform(click());

        assertEquals(meetingNumber+1, meetingApiService.getMeetingNumber());

    }

}
