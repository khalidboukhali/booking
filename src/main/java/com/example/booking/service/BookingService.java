package com.example.booking.service;

import com.example.booking.dto.responseDto.BookingResponse;
import com.example.booking.dto.responseDto.MeetingRoomResponse;
import com.example.booking.entities.Booking;
import com.example.booking.exception.EntityNotFoundException;
import com.example.booking.exception.NoAvailableRoomFoundException;
import com.example.booking.mapper.BookingMapper;
import com.example.booking.mapper.MeetingRoomMapper;
import com.example.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {


    private final MeetingRoomService meetingRoomService;
    private final BookingRepository bookingRepository;

    public BookingService(MeetingRoomService meetingRoomService, BookingRepository bookingRepository) {
        this.meetingRoomService = meetingRoomService;
        this.bookingRepository = bookingRepository;
    }

    private List<MeetingRoomResponse> getAvailableMeetingRooms(LocalDateTime startDate, LocalDateTime endDate) {
        return this.meetingRoomService.getAvailableMeetingRooms(startDate, endDate);
    }

    public void reserveMeetingRoom(long meetingRoomId, LocalDateTime beginAt, LocalDateTime endAt) {
        List<MeetingRoomResponse> availableMeetingRooms = getAvailableMeetingRooms(beginAt, endAt);
        MeetingRoomResponse meetingRoom = this.meetingRoomService.getMeetingRoomById(meetingRoomId);
        if(meetingRoom == null){
            throw new EntityNotFoundException("Meeting room with id " + meetingRoomId + " not found");
        }
        if (!availableMeetingRooms.contains(meetingRoom)) {
            throw new NoAvailableRoomFoundException("The meeting room is not available for the specified period.");
        }

        // Create a new booking
        Booking booking = new Booking();
        booking.setBeginAt(beginAt);
        booking.setEndAt(endAt);
        booking.setMeetingRoom(MeetingRoomMapper.INSTANCE.toEntity(meetingRoom));

        // Save the booking
        bookingRepository.save(booking);
    }

    public void modifyBookingRoom(long bookingId, long newMeetingRoomId, LocalDateTime beginAt, LocalDateTime endAt) {
        // Retrieve the booking to be modified
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new EntityNotFoundException("Booking with id " + bookingId + " not found.");
        }
        Booking booking = optionalBooking.get();

        // Retrieve the new meeting room
        MeetingRoomResponse newMeetingRoom = this.meetingRoomService.getMeetingRoomById(newMeetingRoomId);

        // Check if the new meeting room is available for the specified period
        List<MeetingRoomResponse> availableMeetingRooms = getAvailableMeetingRooms(beginAt, endAt);
        if (!availableMeetingRooms.contains(newMeetingRoom)) {
            throw new IllegalArgumentException("The new meeting room is not available for the specified period.");
        }

        // Update the booking with the new meeting room and period
        booking.setMeetingRoom(MeetingRoomMapper.INSTANCE.toEntity(newMeetingRoom));
        booking.setBeginAt(beginAt);
        booking.setEndAt(endAt);

        // Save the modified booking
        bookingRepository.save(booking);
    }

    public void cancelBooking(long bookingId) {
        // Retrieve the booking to be cancelled
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new EntityNotFoundException("Booking with id " + bookingId + " not found.");
        }
        Booking booking = optionalBooking.get();

        // Delete the booking
        bookingRepository.delete(booking);
    }

    public List<BookingResponse> getAllBookings(){
        List<Booking> bookings = this.bookingRepository.findAll();
        return bookings.stream()
                .map(BookingMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public BookingResponse getBookingById(long id) {
        Booking booking = this.bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking with id " + id + " not found"));
        return BookingMapper.INSTANCE.toDto(booking);
    }
}
