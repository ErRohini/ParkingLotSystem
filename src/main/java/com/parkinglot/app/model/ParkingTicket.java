package com.parkinglot.app.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class ParkingTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    private LocalDateTime parkDate;

    public ParkingTicket(){}


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private int parking_lot_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParking_lot_id() {
        return parking_lot_id;
    }

    public void setParking_lot_id(int parking_lot_id) {
        this.parking_lot_id = parking_lot_id;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getParkDate() {
        return parkDate;
    }

    public void setParkDate(LocalDateTime parkDate) {
        this.parkDate = parkDate;
    }

}
