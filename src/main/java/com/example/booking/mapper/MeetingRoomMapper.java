package com.example.booking.mapper;

import com.example.booking.dto.requestDto.MeetingRoomRequest;
import com.example.booking.dto.responseDto.MeetingRoomResponse;
import com.example.booking.entities.MeetingRoom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MeetingRoomMapper {
    MeetingRoomMapper INSTANCE = Mappers.getMapper(MeetingRoomMapper.class);
    MeetingRoomResponse toDto(MeetingRoom meetingRoom);
    MeetingRoom toEntity(MeetingRoomRequest meetingRoomRequest);
    MeetingRoom toEntity(MeetingRoomResponse meetingRoomResponse);
}
