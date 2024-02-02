package org.artyomnikitin.spring.aop;

import jakarta.persistence.NoResultException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }

    /**ConstraintViolationException HANDLER*/

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ApiError> handleDataIntegrityViolationException(ConstraintViolationException DB_STATE_Violation) {

        ApiError apiError = new ApiError(CONFLICT, DB_STATE_Violation, "User already EXIST");
        return buildResponseEntity(apiError);
    }


    /**NoResultException HANDLER*/
    @ExceptionHandler(NoResultException.class)
    protected ResponseEntity<ApiError> handleNoResultException(NoResultException noResultException) {

        ApiError apiError = new ApiError(UNAUTHORIZED, noResultException, "Please enter a correct login and password");
        return buildResponseEntity(apiError);
    }

}