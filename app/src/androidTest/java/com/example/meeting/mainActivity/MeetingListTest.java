package com.example.meeting.mainActivity;

import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import com.example.meeting.R;
import com.example.meeting.di.DI;
import com.example.meeting.model.Meeting;
import com.example.meeting.service.MeetingApiService;
import com.example.meeting.ui.MainActivity;
import com.example.meeting.utils.DeleteViewAction;
import com.example.meeting.utils.RecyclerViewItemCountAssertion;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasFocus;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.meeting.utils.RecyclerViewMatcher.atPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeetingListTest {
    private static int itemsCount = 6;
    private MainActivity mActivity;

    private MeetingApiService meetingApiService= (MeetingApiService) DI.getMeetingApiService();
    private Meeting meeting ;
    private int index;
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setup(){
        mActivity = mainActivityActivityTestRule.getActivity();
        assertThat(mActivity,notNullValue());
        index=0;
        meeting = meetingApiService.getMeetingList().get(index);
    }

    @Test
    public void testItemDetails(){
        onView(withId(R.id.mainRecyclerView)).check(matches(atPosition(index, hasDescendant(withText(meeting.getMeetingInfos())))));
    }

    @Test
    public void testListMeeting(){
        onView(withId(R.id.mainRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testDeleteButton(){
        onView(allOf(withId(R.id.mainRecyclerView), hasFocus())).check(RecyclerViewItemCountAssertion.withItemCount(itemsCount));
        onView(allOf(withId(R.id.mainRecyclerView), hasFocus())).perform(RecyclerViewActions.actionOnItemAtPosition(index+1, new DeleteViewAction(){}));
        onView(allOf(withId(R.id.mainRecyclerView), hasFocus())).check(RecyclerViewItemCountAssertion.withItemCount(itemsCount-1));
    }

}
