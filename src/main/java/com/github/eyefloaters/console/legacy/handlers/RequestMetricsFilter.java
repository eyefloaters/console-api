package com.github.eyefloaters.console.legacy.handlers;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import com.github.eyefloaters.console.legacy.HttpMetrics;

import java.io.IOException;

@Provider
public class RequestMetricsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Inject
    HttpMetrics httpMetrics;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        httpMetrics.getRequestsCounter().increment();
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        int statusCode = responseContext.getStatus();

        if (statusCode < 300) {
            httpMetrics.getSucceededRequestsCounter().increment();
        } else {
            httpMetrics.getFailedRequestsCounter(statusCode).increment();
        }
    }
}
