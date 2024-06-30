package com.study.hotelland.exception;

public class NotPossibleReservationRoom extends RuntimeException {

    public NotPossibleReservationRoom(String message) {
        super(message);
    }
}
