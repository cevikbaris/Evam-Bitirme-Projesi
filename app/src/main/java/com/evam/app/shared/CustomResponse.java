package com.evam.app.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // if a value is null don't put that to the response.
public class CustomResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    private int code;

    private String status;

    private String message;

    private Object data;

    private String path;


    public CustomResponse() {
        timestamp = new Date();
    }

    public CustomResponse(HttpStatus httpStatus, String message ) {
        this();
        this.code = httpStatus.value(); // like 404 -400 -200
        this.status = httpStatus.name(); // OK - NOT FOUND-  BAD REQUEST
        this.message = message;
    }

    // HttpStatus - Message - Object. Object can be any object.
    public CustomResponse(HttpStatus httpStatus, String message, Object data) {
        this(httpStatus, message);
        this.data = data;
    }

    // Include Url path
    public CustomResponse(HttpStatus httpStatus, String message, Object data,String path){
        this(httpStatus,message,data);
        this.path=path;
    }


    // RESPONSE ENTITY METHODS


    //return HttpStatus.ok - message - object
    public static ResponseEntity<CustomResponse> getOkResponseEntity(Object obj, String success) {
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, success, obj),
                HttpStatus.OK);
    }

    //return HttpStatus.ok - message - object - url path
    public static ResponseEntity<CustomResponse> getOkResponseEntity(Object obj, String success, String path) {
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, success, obj, path),
                HttpStatus.OK);
    }

    // return bad_request with error message
    public static ResponseEntity<CustomResponse> getBadResponseEntity(String error) {
        return new ResponseEntity<>(new CustomResponse(HttpStatus.BAD_REQUEST, error),
                HttpStatus.BAD_REQUEST);
    }

    // check if the object is null then return httpStatus.ok or bad_request with the message.
    public static ResponseEntity<?> getResponseIfObjectNotNull(Object obj, String success, String error) {
        return obj != null
                ? getOkResponseEntity(obj, success)
                : getBadResponseEntity(error);
    }

}
