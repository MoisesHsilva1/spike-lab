package com.moisaas.the_moments.posts.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private String id;
    private String title;
    private String body;
    private Instant createdAt;
}