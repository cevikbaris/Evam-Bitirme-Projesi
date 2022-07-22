package com.evam.app.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)// don't include null variables.
public class ApiError {

    private int Status;

    private String message;

    private String path;

    private Date date = new Date();

    private Map<String,String> validationErrors; // for more than one validation error

    public ApiError(int status, String message, String path) {
        Status = status;
        this.message = message;
        this.path = path;
    }



}

