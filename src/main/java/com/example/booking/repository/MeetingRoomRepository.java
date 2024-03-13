package com.example.booking.repository;

import com.example.booking.entities.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
    @Query("SELECT DISTINCT m FROM MeetingRoom m LEFT JOIN m.bookings b WHERE (b.beginAt > :endAt OR b.endAt < :beginAt) OR b.id IS NULL")
    List<MeetingRoom> findAvailableMeetingRooms(@Param("beginAt") LocalDateTime beginAt, @Param("endAt") LocalDateTime endAt);
}
