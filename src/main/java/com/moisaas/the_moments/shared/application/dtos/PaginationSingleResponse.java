package com.moisaas.the_moments.shared.application.dtos;

public record PaginationSingleResponse<T>(
        boolean success,
        T row
) {
    public PaginationSingleResponse(T data) {
        this(
                true,
                data
        );
    }
}
