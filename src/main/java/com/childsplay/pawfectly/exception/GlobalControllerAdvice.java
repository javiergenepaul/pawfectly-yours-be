package com.childsplay.pawfectly.exception;

import com.childsplay.pawfectly.common.dto.ApiResultModel;
import com.childsplay.pawfectly.exception.custom.FieldValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FieldValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResultModel> handleFieldValidationException(FieldValidationException exception) {
        ApiResultModel customResponse = ApiResultModel.builder()
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .build();

        return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiResultModel> handleUserNotFoundException(UsernameNotFoundException exception) {
        Map<String, Object> error = new HashMap<>();
        error.put("email", "Email does not exist");
        ApiResultModel customResponse = ApiResultModel.builder()
                .message(exception.getMessage())
                .errors(error)
                .build();

        return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler( BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiResultModel> handleBadCredentialsException(BadCredentialsException exception) {
        Map<String, Object> error = new HashMap<>();
        error.put("email", "Email does not exist");
        ApiResultModel customResponse = ApiResultModel.builder()
                .message(exception.getMessage())
                .errors(error)
                .build();

        return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResultModel> handleGlobalException(Exception exception, final HttpServletRequest request) {
        ApiResultModel customResponse = ApiResultModel.builder()
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
