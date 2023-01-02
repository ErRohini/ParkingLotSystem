package com.parkinglot.app.repository;

import com.parkinglot.app.model.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TicketRepository extends JpaRepository<ParkingTicket,Integer> {
    ParkingTicket findByParkDate(LocalDateTime parkDate);

}
