package com.skpGroup.SA.Exceptions;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handle(
            RuntimeException ex
    ){

        HttpStatus status =
                HttpStatus.BAD_REQUEST;

        if(
                ex.getMessage()
                        .contains("Duplicate")
        ){
            status=
                    HttpStatus.CONFLICT;
        }

        if(
                ex.getMessage()
                        .contains("Not found")
        ){
            status=
                    HttpStatus.NOT_FOUND;
        }

        return ResponseEntity
                .status(status)
                .body(
                        Map.of(
                                "error",
                                ex.getMessage()
                        )
                );

    }

}
