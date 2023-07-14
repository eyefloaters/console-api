package com.github.eyefloaters.console.api;

import java.util.concurrent.CompletionStage;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import com.github.eyefloaters.console.api.model.Cluster;
import com.github.eyefloaters.console.api.service.ClusterService;

@Path("/api/v2/cluster")
public class ClusterResource {

    @Inject
    ClusterService clusterService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponseSchema(Cluster.class)
    public CompletionStage<Response> describeCluster() {
        return clusterService.describeCluster()
            .thenApply(Response::ok)
            .exceptionally(error -> Response.serverError().entity(error.getMessage()))
            .thenApply(Response.ResponseBuilder::build);
    }

}