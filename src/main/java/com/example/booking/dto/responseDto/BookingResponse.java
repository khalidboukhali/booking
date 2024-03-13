package com.example.booking.dto.responseDto;

import java.time.LocalDateTime;

public record BookingResponse(
        LocalDateTime beginAt,
        LocalDateTime endAt,
        MeetingRoomResponse meetingRoom
) {}
