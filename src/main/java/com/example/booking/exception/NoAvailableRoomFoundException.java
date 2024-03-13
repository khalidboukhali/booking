package com.example.booking.exception;

public class NoAvailableRoomFoundException extends RuntimeException {
    public NoAvailableRoomFoundException(String message) {
        super(message);
    }
}
