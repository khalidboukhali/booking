package com.example.booking.controller;

import com.example.booking.dto.requestDto.BookingRequest;
import com.example.booking.dto.responseDto.BookingResponse;
import com.example.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/reserve/{meetingRoomId}")
    public ResponseEntity<String> reserveMeetingRoom(@PathVariable long meetingRoomId,
                                                     @RequestBody @Valid BookingRequest bookingRequest) {
        bookingService.reserveMeetingRoom(meetingRoomId, bookingRequest.beginAt(), bookingRequest.endAt());
        return ResponseEntity.status(HttpStatus.CREATED).body("Booking created successfully.");
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<String> modifyBookingRoom(@PathVariable long bookingId,
                                                    @RequestParam long newMeetingRoomId,
                                                    @RequestBody @Valid BookingRequest bookingRequest) {
        bookingService.modifyBookingRoom(bookingId, newMeetingRoomId, bookingRequest.beginAt(), bookingRequest.endAt());
        return ResponseEntity.ok("Booking modified successfully.");
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking canceled successfully.");
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookingResponse> bookings = this.bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookById(@PathVariable long id){
        BookingResponse booking = this.bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

}
