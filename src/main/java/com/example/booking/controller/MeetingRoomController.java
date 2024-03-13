package com.example.booking.controller;


import com.example.booking.dto.requestDto.MeetingRoomRequest;
import com.example.booking.dto.responseDto.MeetingRoomResponse;
import com.example.booking.service.MeetingRoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meetingRooms")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;

    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @GetMapping
    public ResponseEntity<List<MeetingRoomResponse>> getAllMeetingRooms(){
        List<MeetingRoomResponse> books = this.meetingRoomService.getAllMeetingRooms();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingRoomResponse> getBookById(@PathVariable long id){
        MeetingRoomResponse book = this.meetingRoomService.getMeetingRoomById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<String> addMeetingRoom(@RequestBody @Valid MeetingRoomRequest meetingRoomRequest){
        try {
            this.meetingRoomService.addMeetingRoom(meetingRoomRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Meeting room added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding new meeting room: " + e.getMessage());
        }
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<String>  updateMeetingRoom(@PathVariable long roomId, @RequestBody @Valid MeetingRoomRequest meetingRoomRequest){
        try {
            this.meetingRoomService.updateMeetingRoom(roomId, meetingRoomRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Meeting room updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating meeting room: " + e.getMessage());
        }
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteMeetingRoom(@PathVariable long roomId){
        try {
            this.meetingRoomService.deleteMeetingRoomById(roomId);
            return ResponseEntity.status(HttpStatus.OK).body("Meeting room deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting meeting room: " + e.getMessage());
        }
    }
}
