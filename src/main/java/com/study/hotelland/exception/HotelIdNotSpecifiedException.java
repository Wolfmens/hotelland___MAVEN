package com.study.hotelland.exception;

public class HotelIdNotSpecifiedException extends RuntimeException {

    private static final String message = "Hotel Id Not Specified. Please indicate hotel id and repeat the request";

    public HotelIdNotSpecifiedException() {
        super(message);
    }
}
