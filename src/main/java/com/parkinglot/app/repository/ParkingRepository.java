package com.parkinglot.app.repository;


import com.parkinglot.app.model.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingTicket,Integer> {

}
