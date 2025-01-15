package com.fms.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
   @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleException(RuntimeException ex, WebRequest request)
    {
        ApiError error;

        if(ex instanceof FmsException)
        {
            error = new ApiError(HttpStatus.BAD_REQUEST,((FmsException) ex).getMessage(), ex);
        }

        else if(ex instanceof ResourceNotFoundException)
        {
            error = new ApiError(HttpStatus.NOT_FOUND, "Unable to find the requested resource.", ex);
        }

        else
        {
            error = new ApiError(HttpStatus.BAD_REQUEST, "Unexpected error while processing.", ex);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error,headers, error.getStatus());
    }
}
