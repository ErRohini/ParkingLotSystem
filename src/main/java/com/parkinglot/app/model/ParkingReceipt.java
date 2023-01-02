package com.parkinglot.app.model;

import java.time.LocalDateTime;

public class ParkingReceipt {

    private ParkingTicket parkingTicket;

    private LocalDateTime leaveDate;
    private double price;

    public ParkingReceipt(){}

    public ParkingReceipt(ParkingTicket parkingTicket, LocalDateTime leaveDate, double price) {
        this.parkingTicket = parkingTicket;
        this.leaveDate = leaveDate;
        this.price = price;
    }

    public ParkingTicket getParkingTicket() {
        return parkingTicket;
    }

    public void setParkingTicket(ParkingTicket parkingTicket) {
        this.parkingTicket = parkingTicket;
    }

    public LocalDateTime getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDateTime leaveDate) {
        this.leaveDate = leaveDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
