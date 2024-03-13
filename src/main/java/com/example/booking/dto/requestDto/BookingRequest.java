package com.example.booking.dto.requestDto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BookingRequest(
        @NotNull(message = "Begin date cannot be empty.")
        @FutureOrPresent(message = "Begin date must be in the future or present.")
        LocalDateTime beginAt,

        @NotNull(message = "End date cannot be empty.")
        @FutureOrPresent(message = "End date must be in the future or present.")
        LocalDateTime endAt
){}
