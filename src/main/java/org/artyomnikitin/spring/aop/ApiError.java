package org.artyomnikitin.spring.aop;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
class ApiError{

    private Integer status;
    /**Beautify JSON ResponseBody*/
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy @ HH:mm:ss")
    private Date timestamp;
    private String message;

    ApiError(HttpStatus status, Throwable ex) {
        this.timestamp = new Date();
        this.status = status.value();
        this.message =ex.getMessage();
    }
    ApiError(HttpStatus status, Throwable ex, String message) {
        this.timestamp = new Date();
        this.status = status.value();
        this.message = message;
    }
}