package com.moisaas.spike_lab.shared.application.dtos;

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
