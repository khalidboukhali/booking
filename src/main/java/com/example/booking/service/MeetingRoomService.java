package com.example.booking.service;

import com.example.booking.dto.requestDto.MeetingRoomRequest;
import com.example.booking.dto.responseDto.MeetingRoomResponse;
import com.example.booking.entities.MeetingRoom;
import com.example.booking.exception.EntityNotFoundException;
import com.example.booking.exception.NoAvailableRoomFoundException;
import com.example.booking.mapper.MeetingRoomMapper;
import com.example.booking.repository.MeetingRoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeetingRoomService {
    private final MeetingRoomRepository meetingRoomRepository;

    public MeetingRoomService(MeetingRoomRepository meetingRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
    }

    public MeetingRoomResponse getMeetingRoomById(long id) {
        MeetingRoom meetingRoom = this.meetingRoomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meeting room with id " + id + " not found"));
        return MeetingRoomMapper.INSTANCE.toDto(meetingRoom);
    }

    public List<MeetingRoomResponse> getAllMeetingRooms() {
        List<MeetingRoom> meetingRooms = this.meetingRoomRepository.findAll();
        return meetingRooms.stream()
                .map(MeetingRoomMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public void addMeetingRoom(MeetingRoomRequest meetingRoomRequest) {
        MeetingRoom meetingRoom = MeetingRoomMapper.INSTANCE.toEntity(meetingRoomRequest);
        this.meetingRoomRepository.save(meetingRoom);
    }

    public void updateMeetingRoom(long roomId, MeetingRoomRequest updatedMeetingRoom) {
        MeetingRoom meetingRoom = this.meetingRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Meeting room with id " + roomId + " not found"));
        meetingRoom.setName(updatedMeetingRoom.name());
        meetingRoom.setCapacity(updatedMeetingRoom.capacity());
        this.meetingRoomRepository.save(meetingRoom);
    }

    public void deleteMeetingRoomById(long id) {
        Optional<MeetingRoom> meetingRoomOptional = this.meetingRoomRepository.findById(id);
        if (meetingRoomOptional.isPresent()) {
            this.meetingRoomRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Meeting room with id " + id + " not found");
        }
    }

    public List<MeetingRoomResponse> getAvailableMeetingRooms(LocalDateTime startDate, LocalDateTime endDate) {
        List<MeetingRoom> availableMeetingRooms = this.meetingRoomRepository.findAvailableMeetingRooms(startDate, endDate);
        if (availableMeetingRooms.isEmpty()) {
            throw new NoAvailableRoomFoundException("No meeting rooms available for the specified period.");
        }
        return availableMeetingRooms.stream()
                .map(MeetingRoomMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}
