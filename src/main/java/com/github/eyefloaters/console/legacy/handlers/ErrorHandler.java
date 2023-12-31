package com.github.eyefloaters.console.legacy.handlers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ErrorHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        return CommonHandler.processFailure(exception).build();
    }

}
