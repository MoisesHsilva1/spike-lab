package com.moisaas.the_moments.posts.application.dtos;

import com.moisaas.the_moments.tags.application.dtos.TagDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private String id;
    private String title;
    private String body;
    private String imageUrl;
    private double stars;
    private List<TagDto> tags;
    private LocalDateTime updateAt;
    private LocalDateTime createdAt;
}