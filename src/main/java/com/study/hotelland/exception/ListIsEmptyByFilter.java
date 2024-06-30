package com.study.hotelland.exception;

import java.text.MessageFormat;

public class ListIsEmptyByFilter extends RuntimeException {

    private static final String customMessage = "Result filter by {0} is empty, check filter params";

    public ListIsEmptyByFilter(String message) {
        super(MessageFormat.format(customMessage,message));
    }
}
