package com.talkit.configuration.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;
}
