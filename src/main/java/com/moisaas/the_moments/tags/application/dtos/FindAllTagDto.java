package com.moisaas.the_moments.tags.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAllTagDto {
    private Integer offset = 0;
    private Integer limit = 10;
    private String name;
}
