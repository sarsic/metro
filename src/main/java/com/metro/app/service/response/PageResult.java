package com.metro.app.service.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class PageResult<T> {
    @Schema(name = "totalPages",
            type = "number",
            format = "integer",
            description = "Total number of pages",
            example = "1")
    private long totalPages;
    private List<T> items;

    protected PageResult() {

    }

    public PageResult(final long totalPages, final List<T> items) {
        this.totalPages = totalPages;
        this.items = items;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public List<T> getItems() {
        return items;
    }
}
