package com.example.booking.mapper;

import com.example.booking.dto.responseDto.BookingResponse;
import com.example.booking.entities.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    BookingResponse toDto(Booking booking);
}
