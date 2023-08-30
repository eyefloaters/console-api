package com.github.eyefloaters.console.api.model;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

import org.eclipse.microprofile.openapi.annotations.enums.Explode;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import com.github.eyefloaters.console.api.support.ErrorCategory;
import com.github.eyefloaters.console.api.support.PositiveInteger;

public class ListFetchParams {

    @QueryParam("sort")
    @Parameter(name = "sort",
        in = ParameterIn.QUERY,
        explode = Explode.FALSE,
        schema = @Schema(implementation = String[].class),
        description = """
            Comma-separated list of fields by which the result set will be ordered.
            The sort order for each sort field will be ascending unless it is prefixed
            with a minus (U+002D HYPHEN-MINUS, "-"), in which case it will be descending.

            Unrecognized field names or fields of type `object` or `array` will be
            ignored.
            """)
    String sort;

    @QueryParam("page[size]")
    @PositiveInteger(digits = 3, category = ErrorCategory.INVALID_QUERY_PARAMETER, source = "page[size]")
    @DefaultValue("10")
    @Parameter(name = "page[size]",
        in = ParameterIn.QUERY,
        schema = @Schema(implementation = int.class))
    String pageSize;

    @QueryParam("page[number]")
    @PositiveInteger(digits = 3, category = ErrorCategory.INVALID_QUERY_PARAMETER, source = "page[number]")
    @DefaultValue("1")
    @Parameter(name = "page[number]",
        in = ParameterIn.QUERY,
        schema = @Schema(implementation = int.class))
    String pageNumber;

    public String getSort() {
        return sort;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getPageNumber() {
        return pageNumber;
    }

}
