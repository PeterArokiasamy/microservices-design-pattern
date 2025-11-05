package com.arokia.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//To the message we need to send response status code right? in below method we send status message.
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistsException extends RuntimeException {
    //Any Customer Exception should extends RuntimeException


    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
    /* Create a constructor for this class which is going to accept the exception message.
       Once this constructor is invoked with this message details, we need to make sure
       we are invoking the parent constructor also using super(message).
     */
}
