package com.website.enbookingbe.utils;

import lombok.Getter;

import static com.google.common.base.Preconditions.checkState;

@Getter
public class PageRequest {
    private final int page;
    private final int size;

    private PageRequest(int page, int size) {
        checkState(page >= 0, "Page must be greater than or equal to 0");
        checkState(size > 0, "Size must be greater than 0");

        this.page = page;
        this.size = size;
    }

    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size);
    }

    public int getOffset() {
        return (page - 1) * size;
    }
}
