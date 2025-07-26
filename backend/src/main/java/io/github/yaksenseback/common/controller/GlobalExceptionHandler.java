package io.github.yaksenseback.common.controller;

import io.github.yaksenseback.common.exception.ApplicationException;
import io.github.yaksenseback.common.exception.ResourceNotFoundException;
import io.github.yaksenseback.common.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UnauthorizedException.class)
    public ProblemDetail handleApplicationException(UnauthorizedException exception) {
        log.error("Unauthorized exception occurred: {}", exception.getMessage(), exception);
        return getProblemDetail(HttpStatus.UNAUTHORIZED, exception);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail emailExceptionHandler(ResourceNotFoundException exception) {
        log.error("Resource not found exception occurred: {}", exception.getMessage(), exception);
        return getProblemDetail(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(value = {Exception.class})
    public ProblemDetail handleException(Exception exception) {
        log.error("An unexpected error occurred: {}", exception.getMessage(), exception);
        return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ProblemDetail handleApplicationException(RuntimeException exception) {
        log.error("Illegal argument or state exception occurred: {}", exception.getMessage(), exception);
        return getProblemDetail(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler({ApplicationException.class})
    public ProblemDetail handleApplicationException(ApplicationException exception) {
        log.error("Application exception occurred: {}", exception.getMessage(), exception);
        return getProblemDetail(HttpStatus.BAD_REQUEST, exception);
    }

    private static ProblemDetail getProblemDetail(HttpStatus status, Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("exception", exception.getClass().getSimpleName());
        return problemDetail;
    }

    private static String getTitle(Exception exception) {
        if (exception instanceof IllegalStateException) {
            return "Invalid State";
        } else if (exception instanceof IllegalArgumentException) {
            return "Invalid Argument";
        } else if (exception instanceof ApplicationException) {
            return "Application Error";
        }
        return "Error";
    }

}
