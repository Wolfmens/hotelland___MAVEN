package com.study.hotelland.exception;

public class IncorrectRequestFilterFiledByArrivalOrDepartureDate extends RuntimeException {

    private static final String message = "Some of the dates fields are not specified, " +
            "check the fields arrival or departure";

    public IncorrectRequestFilterFiledByArrivalOrDepartureDate() {
        super(message);
    }
}
