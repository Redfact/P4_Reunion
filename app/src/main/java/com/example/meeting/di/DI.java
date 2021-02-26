package com.example.meeting.di;

import com.example.meeting.service.MeetingApiService;
import com.example.meeting.service.MeetingService;

public class DI {
    private static MeetingService service = new MeetingApiService();

    /**
     * Get an instance on @{@link MeetingApiService}
     * @return
     */
    public static MeetingService getMeetingApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link MeetingApiService}. Useful for tests, so we ensure the context is clean.
     * @return
     */
    public static MeetingService getNewInstanceApiService() {
        return new MeetingApiService();
    }
}
