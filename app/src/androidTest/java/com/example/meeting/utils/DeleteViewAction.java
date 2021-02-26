package com.example.meeting.utils;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.example.meeting.R;

import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;

public abstract class DeleteViewAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on specific button";
    }

    @Override
    public void perform(UiController uiController, View view) {
        View button = view.findViewById(R.id.deleteButton);
        button.performClick();
    }

}
