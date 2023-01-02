package com.parkinglot.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ParkingLot{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String vehicleType;
    private int parking_lot_size;
    private int parking_rate;
    private int parking_exceed_price;

    public ParkingLot() {

    }

    public ParkingLot(int id, String vehicleType, int parking_lot_size, int parking_rate, int parking_exceed_price) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.parking_lot_size = parking_lot_size;
        this.parking_rate = parking_rate;
        this.parking_exceed_price = parking_exceed_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id =id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getParking_lot_size() {
        return parking_lot_size;
    }

    public void setParking_lot_size(int parking_lot_size) {
        this.parking_lot_size = parking_lot_size;
    }

    public int getParking_rate() {
        return parking_rate;
    }

    public void setParking_rate(int parking_rate) {
        this.parking_rate = parking_rate;
    }

    public int getParking_exceed_price() {
        return parking_exceed_price;
    }

    public void setParking_exceed_price(int parking_exceed_price) {
        this.parking_exceed_price = parking_exceed_price;
    }
}
