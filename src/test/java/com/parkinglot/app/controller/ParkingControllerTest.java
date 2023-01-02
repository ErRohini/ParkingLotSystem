package com.parkinglot.app.controller;

import com.parkinglot.app.Controller.ParkingController;
import com.parkinglot.app.exception.ParkingLotNotFoundException;
import com.parkinglot.app.model.ParkingLot;
import com.parkinglot.app.model.ParkingReceipt;
import com.parkinglot.app.model.ParkingTicket;
import com.parkinglot.app.model.Vehicle;
import com.parkinglot.app.service.ParkingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ParkingControllerTest {

    @InjectMocks
    ParkingController parkingController;

    @Mock
    ParkingService parkingService;

    ParkingTicket parkingTicket;
    Vehicle vehicle;
    ParkingLot parkingLot;

    ParkingReceipt parkingReceipt;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        parkingLot = new ParkingLot();
        parkingLot.setId(1);
        parkingLot.setParking_lot_size(2);
        parkingLot.setVehicleType("BIKE");
        parkingLot.setParking_rate(30);
        parkingLot.setParking_exceed_price(10);

        parkingReceipt=new ParkingReceipt();
        parkingReceipt.setParkingTicket(parkingTicket);
        parkingReceipt.setPrice(10.0);
        parkingReceipt.setLeaveDate(LocalDateTime.now());


    }

    @Test
    @DisplayName("Save All Parking Lots")
    void testCreateParkingLots()
    {
        // Given
        List<ParkingLot> parkingLotList=new ArrayList<>();
        parkingLotList.add(parkingLot);
        // when
        when(parkingService.saveAllParkingLots(parkingLotList)).thenReturn(parkingLotList);
        // Then
       ResponseEntity<List<ParkingLot>> responseParkinglot= parkingController.createParkingLots(parkingLotList);
        assertThat(responseParkinglot.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Save Parking lot with empty lots list")
    void testCreateParkingLotsForEmptyLots()
    {
        List<ParkingLot> parkingLotList=new ArrayList<>();
        ParkingLotNotFoundException notFoundException=assertThrows(ParkingLotNotFoundException.class,
                ()->{when(parkingService.saveAllParkingLots(any())).thenReturn(parkingLotList);
                    parkingController.createParkingLots(parkingLotList);
                });
        assertThat(notFoundException.getMessage()).isEqualTo("Parking Lot List Missing");

    }


    @Test
    @DisplayName("Fetch Available Parking Lot")
    void testGetParkingLot()
    {
        // Given
        int parkinglot_id=1;
        // when
        when(parkingService.getParkingLotById(parkinglot_id)).thenReturn(Optional.ofNullable(parkingLot));

        // then
        ResponseEntity<Optional<ParkingLot>> responseEntity=parkingController.getParkingLot(parkinglot_id);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertEquals(responseEntity.getBody().isPresent(),true);

    }

    @Test
    void testGetParkingLotForAbsentId()
    {
       int parkinglot_id=3;
        parkingLot=null;
        ParkingLotNotFoundException notFoundException=assertThrows(ParkingLotNotFoundException.class,
                ()->{when(parkingService.getParkingLotById(parkinglot_id)).thenReturn(Optional.ofNullable(parkingLot));
                parkingController.getParkingLot(parkinglot_id);
        });
        assertThat(notFoundException.getMessage()).isEqualTo("Parking Lot Unavailable");
    }

    @Test
    void testParkingTicket()
    {
        when(parkingService.getParkingticket(vehicle)).thenReturn(parkingTicket);
        ResponseEntity<ParkingTicket> ticket=parkingController.generateParkingTicket(vehicle);
        assertThat(ticket.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testgenerateParkingReceipt()
    {
        when(parkingService.generateReciept(parkingTicket)).thenReturn(parkingReceipt);
        ResponseEntity<ParkingReceipt> responseEntity=parkingController.generateParkingReceipt(parkingTicket);
        assertThat(responseEntity.getBody().getPrice()).isEqualTo(10.0);
    }



}
