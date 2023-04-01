package ru.tinkoff.edu.java.scrapper.controller.exception;

import com.natishark.course.tinkoff.bot.dto.ApiErrorResponse;
import com.natishark.course.tinkoff.bot.exception.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ScrapperExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    protected ApiErrorResponse handleResourceNotFound(ResourceNotFoundException e, WebRequest request) {
        return convertExceptionToApiErrorResponse(e, request, HttpStatus.NOT_FOUND.toString(), null);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected ApiErrorResponse handleMissingRequestHeader(MissingRequestHeaderException e, WebRequest request) {
        return convertExceptionToApiErrorResponse(e, request, HttpStatus.BAD_REQUEST.toString(), null);
    }
}
