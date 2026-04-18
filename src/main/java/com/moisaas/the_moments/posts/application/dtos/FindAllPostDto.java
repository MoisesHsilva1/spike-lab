package com.moisaas.the_moments.posts.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAllPostDto {
    private Integer offset = 0;
    private Integer limit = 10;
    private Double stars;
}
