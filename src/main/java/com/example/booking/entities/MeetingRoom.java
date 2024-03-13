package com.example.booking.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int capacity;

    @OneToMany(mappedBy = "meetingRoom", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
