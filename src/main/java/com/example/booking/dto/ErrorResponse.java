package com.example.booking.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {}
