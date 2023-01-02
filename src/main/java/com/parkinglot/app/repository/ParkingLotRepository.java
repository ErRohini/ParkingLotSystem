package com.parkinglot.app.repository;


import com.parkinglot.app.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Integer> {
  Optional<ParkingLot> findByVehicleType(String vehicleType);
}
