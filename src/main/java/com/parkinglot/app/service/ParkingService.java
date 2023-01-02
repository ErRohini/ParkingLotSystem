package com.parkinglot.app.service;


import com.parkinglot.app.exception.ParkingLotNotFoundException;
import com.parkinglot.app.model.ParkingLot;
import com.parkinglot.app.model.ParkingReceipt;
import com.parkinglot.app.model.Vehicle;
import com.parkinglot.app.repository.ParkingLotRepository;
import com.parkinglot.app.repository.TicketRepository;
import com.parkinglot.app.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parkinglot.app.model.ParkingTicket;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    ParkingTicket parkingTicket=new ParkingTicket();

    ParkingReceipt parkingReceipt=new ParkingReceipt();

    public ParkingTicket getParkingticket(Vehicle vehicle)
    {
       ParkingLot parkingLot=getParkingLotByVehicle(vehicle.getVehicle_type()).orElseThrow(ParkingLotNotFoundException::new);
                vehicle = vehicleRepository.save(vehicle);
            if(parkingLot.getParking_lot_size()!=0){
                parkingTicket.setParking_lot_id(parkingLot.getId());
                parkingTicket.setVehicle(vehicle);
                parkingTicket.setParkDate(java.time.LocalDateTime.now());
                parkingLot.setParking_lot_size(parkingLot.getParking_lot_size()-1);
                parkingLotRepository.save(parkingLot);
                parkingTicket = ticketRepository.save(parkingTicket);
            }
            else {
                throw new ParkingLotNotFoundException("Parking Lot Not Available for Vehicle "+parkingLot.getVehicleType());
            }
            return parkingTicket;
    }

    // Get Parking lot by vehicle type
    public Optional<ParkingLot> getParkingLotByVehicle(String vehicleType)
    {
      return  parkingLotRepository.findByVehicleType(vehicleType);
    }
    public List<ParkingLot> saveAllParkingLots(List<ParkingLot>  parkingLotList)
    {
       return parkingLotRepository.saveAll(parkingLotList) ;
    }

    public Optional<ParkingLot> getParkingLotById(int id)
    {
        Optional<ParkingLot> parkingLot= parkingLotRepository.findById(id);
        return parkingLot;
    }
   public ParkingReceipt generateReciept(ParkingTicket parkingTicket)
    {
        updateParkinglot(parkingTicket.getParking_lot_id());
        java.time.LocalDateTime leaveDate = java.time.LocalDateTime.now();
        ParkingLot parkingLot= getParkingLotById(parkingTicket.getParking_lot_id()).orElseThrow(ParkingLotNotFoundException::new);
        parkingReceipt.setParkingTicket(parkingTicket);
        parkingReceipt.setLeaveDate(leaveDate);
        int hours = (int) ChronoUnit.HOURS.between(parkingTicket.getParkDate(), leaveDate);
        if (hours <= 2) {
            parkingReceipt.setPrice(parkingLot.getParking_rate());
        }
                parkingReceipt.setPrice(parkingLot.getParking_rate() + ((hours - 2) * parkingLot.getParking_exceed_price()));
        return  parkingReceipt;
    }
    public void updateParkinglot(int parkingId) {
       ParkingLot parkingLot= parkingLotRepository.findById(parkingId).orElseThrow(ParkingLotNotFoundException::new);
        parkingLot.setParking_lot_size(parkingLot.getParking_lot_size()+1);
       parkingLotRepository.save(parkingLot);
    }
}
