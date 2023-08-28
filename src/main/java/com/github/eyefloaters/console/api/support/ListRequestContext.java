package com.github.eyefloaters.console.api.support;

import java.net.URI;
import java.util.Map;

import jakarta.ws.rs.core.UriBuilder;

import com.github.eyefloaters.console.api.model.DataListResponse;
import com.github.eyefloaters.console.api.model.ListFetchParams;
import com.github.eyefloaters.console.api.model.Resource;

public class ListRequestContext {

    final URI requestUri;
    final String sort;
    final int pageSize;
    final int pageNumber;
    final int offsetBegin;
    final int offsetEnd;

    int totalRecords = 0;
    int offset = -1;
    int recordsIncluded = 0;

    public ListRequestContext(URI requestUri, ListFetchParams listParams) {
        this.requestUri = requestUri;
        sort = listParams.getSort();
        pageSize = Integer.parseInt(listParams.getPageSize());
        pageNumber = Integer.parseInt(listParams.getPageNumber());
        offsetBegin = pageSize * (pageNumber - 1);
        offsetEnd = offsetBegin + pageSize;
    }

    public <T> T tally(T item) {
        totalRecords++;
        return item;
    }

    /**
     * @param entity the current entity in the result set
     * @return true when the record falls before the requested paged, otherwise false
     */
    public boolean beforePageBegin(Object entity) {
        return ++offset < offsetBegin;
    }

    /**
     * @param entity the current entity in the result set
     * @return true when the record falls after the requested paged, otherwise false
     */
    public boolean beyondPageEnd(Object entity) {
        return ++offset > offsetEnd;
    }

    public String getSort() {
        return sort;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    /**
     * Create the base/non-paged UriBuilder to be used to initialize the links
     * for first, last, prev, and next.
     *
     * XXX: Update this method to include {@code filter}`s once implemented.
     */
    UriBuilder getPageUriBuilder() {
        UriBuilder builder = UriBuilder.fromUri(requestUri).replaceQuery(null);

        if (sort != null) {
            builder.queryParam("sort", sort);
        }

        return builder;
    }

    String buildPageLink(int pageSize, int pageNumber) {
        return getPageUriBuilder()
                .queryParam("page[size]", pageSize)
                .queryParam("page[number]", pageNumber)
                .build()
                .toString();
    }

    String maybeBuildPageLink(boolean enabled, int pageSize, int pageNumber) {
        if (enabled) {
            return buildPageLink(pageSize, pageNumber);
        }
        return null;
    }

    public <T extends Resource<?>> DataListResponse<T> addPageLinks(DataListResponse<T> response) {
        response.addMeta("page", Map.of("total", getTotalRecords()));

        int lastPage = totalRecords / pageSize;

        response.addLink("self", buildPageLink(pageSize, pageNumber));
        response.addLink("first", buildPageLink(pageSize, 1));
        response.addLink("last", buildPageLink(pageSize, lastPage));
        response.addLink("prev", maybeBuildPageLink(pageNumber > 1, pageSize, pageNumber - 1));
        response.addLink("next", maybeBuildPageLink(pageNumber < lastPage, pageSize, pageNumber + 1));

        return response;
    }
}
