package com.vao.agenda.repository;

import com.vao.agenda.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByDateHourBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
