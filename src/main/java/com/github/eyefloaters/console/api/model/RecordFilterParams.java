package com.github.eyefloaters.console.api.model;

import java.time.Instant;

import jakarta.validation.constraints.AssertTrue;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import com.github.eyefloaters.console.api.support.ErrorCategory;
import com.github.eyefloaters.console.api.support.RangeLimitedInteger;

public class RecordFilterParams {

    @QueryParam("filter[partition]")
    @Parameter(description = "Retrieve messages only from this partition")
    @RangeLimitedInteger(min = "0", category = ErrorCategory.INVALID_QUERY_PARAMETER, source = "filter[partition]")
    String partition;

    @QueryParam("filter[offset]")
    @Parameter(description = """
            Retrieve messages with an offset equal to or greater than this offset.
            If both `filter[timestamp]` and `filter[offset]` are requested, `filter[timestamp]`
            is given preference.
            """)
    @RangeLimitedInteger(min = "0", category = ErrorCategory.INVALID_QUERY_PARAMETER, source = "filter[offset]")
    String offset;

    @QueryParam("filter[timestamp]")
    @Parameter(
        description = """
            Retrieve messages with a timestamp equal to or later than this timestamp.
            If both `filter[timestamp]` and `filter[offset]` are requested, `filter[timestamp]`
            is given preference.
            """,
        schema = @Schema(format = "date-time"))
    String timestamp;

    @QueryParam("page[size]")
    @DefaultValue("20")
    @Parameter(description = "Limit the number of records fetched and returned")
    @RangeLimitedInteger(category = ErrorCategory.INVALID_QUERY_PARAMETER, source = "page[size]")
    String limit;

    @QueryParam("maxValueLength")
    @Parameter(description = "Maximum length of string values returned in the response. "
            + "Values with a length that exceeds this parameter will be truncated. When this parameter is not "
            + "included in the request, the full string values will be returned.")
    @RangeLimitedInteger(category = ErrorCategory.INVALID_QUERY_PARAMETER, source = "maxValueLength")
    String maxValueLength;

    @AssertTrue(message = "invalid timestamp")
    public boolean isTimestampValid() {
        if (timestamp == null) {
            return true;
        }

        try {
            return Instant.parse(timestamp).isAfter(Instant.ofEpochMilli(-1));
        } catch (Exception e) {
            return false;
        }
    }

    public Integer getPartition() {
        return partition != null ? Integer.parseInt(partition) : null;
    }

    public Long getOffset() {
        return offset != null ? Long.parseLong(offset) : null;
    }

    public Instant getTimestamp() {
        return timestamp != null ? Instant.parse(timestamp) : null;
    }

    public Integer getLimit() {
        return limit != null ? Integer.parseInt(limit) : null;
    }

    public Integer getMaxValueLength() {
        return maxValueLength != null ? Integer.parseInt(maxValueLength) : null;
    }
}
