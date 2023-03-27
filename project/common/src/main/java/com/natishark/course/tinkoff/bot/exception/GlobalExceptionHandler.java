package com.natishark.course.tinkoff.bot.exception;

import com.natishark.course.tinkoff.bot.dto.ApiErrorResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NotNull HttpMessageNotReadableException ex,
            @NotNull HttpHeaders headers,
            HttpStatusCode status,
            @NotNull WebRequest request
    ) {
        return ResponseEntity.badRequest().body(
                convertExceptionToApiErrorResponse(ex, request, status.toString(), null)
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NotNull MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request
    ) {
        return ResponseEntity.badRequest().body(
                convertExceptionToApiErrorResponse(
                        ex,
                        request,
                        HttpStatus.BAD_REQUEST.toString(),
                        ex.getBindingResult().getFieldError() == null ?
                                null :
                                ex.getBindingResult().getFieldError().getDefaultMessage()
                )
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiErrorResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e, WebRequest request) {
        return convertExceptionToApiErrorResponse(e, request, HttpStatus.BAD_REQUEST.toString(), null);
    }

    protected ApiErrorResponse convertExceptionToApiErrorResponse(
            Exception e,
            WebRequest request,
            String code,
            String message
    ) {
        return new ApiErrorResponse(
                request.getDescription(false),
                code,
                e.getClass().getName(),
                message != null ? message : e.getMessage(),
                Arrays.stream(e.getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.toList())
        );
    }
}
