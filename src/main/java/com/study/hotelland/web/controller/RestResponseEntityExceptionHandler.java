package com.study.hotelland.web.controller;

import com.study.hotelland.exception.*;
import com.study.hotelland.web.dto.exception.ExceptionMessage;
import jakarta.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundEntityException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ExceptionMessage exceptionEntityNotFound(NotFoundEntityException ex) {
        return new ExceptionMessage(ex.getLocalizedMessage());
    }

    @ExceptionHandler(NotPossibleReservationRoom.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionMessage exceptionIfNotPossibleReservationRoomByChooseDates(NotPossibleReservationRoom ex) {
        return new ExceptionMessage(ex.getLocalizedMessage());
    }

    @ExceptionHandler(HttpServerErrorException.BadGateway.class)
    @ResponseStatus(code = HttpStatus.BAD_GATEWAY)
    public ExceptionMessage exceptionServerBadGateway500(HttpServerErrorException ex) {
        return new ExceptionMessage(ex.getLocalizedMessage());
    }

    @ExceptionHandler(ListIsEmptyByFilter.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionMessage exceptionServerBadGateway500(ListIsEmptyByFilter ex) {
        return new ExceptionMessage(ex.getLocalizedMessage());
    }

    @ExceptionHandler(IncorrectRequestFilterFiledByArrivalOrDepartureDate.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionMessage exceptionIfIncorrectFieldsArriveOrDeparture
            (IncorrectRequestFilterFiledByArrivalOrDepartureDate ex) {
        return new ExceptionMessage(ex.getLocalizedMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ExceptionMessage validationNameOrEmailAlreadyExists
            (ValidationException ex) {
        return new ExceptionMessage(ex.getLocalizedMessage());
    }

    @ExceptionHandler(HotelIdNotSpecifiedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage hotelIdNotSpecifiedException(HotelIdNotSpecifiedException ex) {
        return new ExceptionMessage(ex.getLocalizedMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionMessage exceptionNotValidParams(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        List<String> messages = bindingResult
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        String exceptionMessage = String.join("; ", messages);

        return new ExceptionMessage(exceptionMessage);
    }


}
