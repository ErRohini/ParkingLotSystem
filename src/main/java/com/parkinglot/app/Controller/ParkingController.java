package com.parkinglot.app.Controller;


import com.parkinglot.app.exception.ParkingLotNotFoundException;
import com.parkinglot.app.model.ParkingLot;
import com.parkinglot.app.model.ParkingReceipt;
import com.parkinglot.app.model.ParkingTicket;
import com.parkinglot.app.model.Vehicle;
import com.parkinglot.app.repository.ParkingLotRepository;
import com.parkinglot.app.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/park")
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    private ParkingLot parkingLot=new ParkingLot();
    @Autowired
    private ParkingLotRepository parkingLotRepository;

       // Create Parking Lots
        @PostMapping("/parkinglot")
        public ResponseEntity<List<ParkingLot>> createParkingLots(@RequestBody List<ParkingLot> parkingLotList)
        {
            if(parkingLotList.isEmpty())
            {
                throw new ParkingLotNotFoundException("Parking Lot List Missing");
            }
           return new ResponseEntity<>(parkingService.saveAllParkingLots(parkingLotList), HttpStatus.CREATED) ;
        }
        // Get available parking lot
        @GetMapping("/parkinglot/{id}")
        public ResponseEntity<Optional<ParkingLot>> getParkingLot(@PathVariable("id") int id)
        {
            Optional<ParkingLot> parkingLot=parkingService.getParkingLotById(id);
            if (!parkingLot.isPresent())
            {
                throw new ParkingLotNotFoundException("Parking Lot Unavailable");
            }
          return  new ResponseEntity<>(parkingService.getParkingLotById(id),HttpStatus.OK);

        }
        // Generate Ticket
        @GetMapping("/ticket")
        public ResponseEntity<ParkingTicket> generateParkingTicket(@RequestBody Vehicle vehicle)
        {
           ParkingTicket ticket=parkingService.getParkingticket(vehicle);
            return new ResponseEntity<>(ticket,HttpStatus.OK);
        }
        // Generate Receipt
        @GetMapping("/receipt")
        public ResponseEntity<ParkingReceipt> generateParkingReceipt(@RequestBody ParkingTicket parkingTicket)
        {
            // update the parking lot for new coming vehicle
            ParkingReceipt parkingReceipt= parkingService.generateReciept(parkingTicket);
            return new ResponseEntity<>(parkingReceipt,HttpStatus.OK);
        }



}
