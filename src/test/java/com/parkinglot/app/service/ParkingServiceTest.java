package com.parkinglot.app.service;

import com.parkinglot.app.exception.ParkingLotNotFoundException;
import com.parkinglot.app.model.ParkingLot;
import com.parkinglot.app.model.ParkingReceipt;
import com.parkinglot.app.model.ParkingTicket;
import com.parkinglot.app.model.Vehicle;
import com.parkinglot.app.repository.ParkingLotRepository;
import com.parkinglot.app.repository.ParkingRepository;
import com.parkinglot.app.repository.TicketRepository;
import com.parkinglot.app.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParkingServiceTest {

    @InjectMocks
    ParkingService parkingService;

    @Mock
    ParkingRepository parkingRepository;
    @Mock
    TicketRepository ticketRepository;

    @Mock
    ParkingLotRepository parkingLotRepository;

    @Mock
    VehicleRepository vehicleRepository;

    ParkingTicket parkingTicket;
    Vehicle vehicle;
    ParkingReceipt parkingReceipt;

    ParkingLot parkingLot;

    @BeforeEach
    void setup() throws Exception
    {
        MockitoAnnotations.openMocks(this);
        vehicle=new Vehicle();
        vehicle.setId(1);
        vehicle.setVehicle_type("BIKE");
        vehicle.setColour("White");
        vehicle.setRegistrationNumber("MH12UK3883");

        parkingLot=new ParkingLot();

        parkingLot.setId(1);
        parkingLot.setVehicleType("BIKE");
        parkingLot.setParking_rate(30);
        parkingLot.setParking_exceed_price(10);
        parkingLot.setParking_lot_size(3);

        parkingTicket=new ParkingTicket();
        parkingTicket.setParking_lot_id(1);
        parkingTicket.setVehicle(vehicle);
        parkingTicket.setParking_lot_id(1);
        parkingTicket.setParkDate(java.time.LocalDateTime.now());

    }
    @Test
    void getParkingTicketTest()
    {
        // given
        String vehicleType="BIKE";
        // when
        when(parkingService.getParkingLotByVehicle(vehicleType)).thenReturn(Optional.ofNullable(parkingLot));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(parkingLotRepository.save(parkingLot)).thenReturn(parkingLot);
        when(ticketRepository.save(any(ParkingTicket.class))).thenReturn(parkingTicket);
        ParkingTicket ticket=parkingService.getParkingticket(vehicle);
        assertThat(ticket.getVehicle().getVehicle_type()).isEqualTo(vehicleType);
    }

    @Test
    void checkParkingTicketWithoutLot()
    {
        // given
        String vehicleType="BIKE";
        parkingLot.setParking_lot_size(0);
        // when
        ParkingLotNotFoundException notFoundException=assertThrows(ParkingLotNotFoundException.class,
                ()->{when(parkingService.getParkingLotByVehicle(vehicleType)).thenReturn(Optional.ofNullable(parkingLot));
                    parkingService.getParkingticket(vehicle);  });
        assertThat(notFoundException.getMessage()).isEqualTo("Parking Lot Not Available for Vehicle "+vehicleType);
    }

    @Test
    void generateRecieptTest()
    {
        ParkingLot parkingLot1=new ParkingLot();
        parkingLot1.setId(1);
        parkingLot1.setVehicleType("BIKE");
        parkingLot1.setParking_rate(30);
        parkingLot1.setParking_exceed_price(10);
        parkingLot1.setParking_lot_size(3);
        // when
        when(parkingService.getParkingLotById(anyInt())).thenReturn(Optional.ofNullable(parkingLot1));
        when(parkingLotRepository.save(parkingLot1)).thenReturn(parkingLot1);
        when(parkingLotRepository.findById(anyInt())).thenReturn(Optional.of(parkingLot1));
        when(parkingLotRepository.findById(1)).thenReturn(Optional.ofNullable(parkingLot1));
        parkingReceipt=parkingService.generateReciept(parkingTicket);

        assertThat(parkingReceipt.getPrice()).isEqualTo(10.0);

    }

    @Test
    void saveAllParkingLotsTest()
    {
        //  gieven
        List<ParkingLot> parkingLotList=new ArrayList<>();
        parkingLotList.add(parkingLot);

        // when
        when(parkingLotRepository.saveAll(parkingLotList)).thenReturn(parkingLotList);
        assertThat(parkingService.saveAllParkingLots(parkingLotList)).isEqualTo(parkingLotList);
    }

}
