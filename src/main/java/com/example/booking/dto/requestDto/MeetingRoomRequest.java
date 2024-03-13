package com.example.booking.dto.requestDto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MeetingRoomRequest(

        @Size(min = 3, message = "Name should at least contain {min} characters")
        String name,

        @Positive(message = "Capacity must be a positive integer")
        int capacity
){}
