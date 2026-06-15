package com.moisaas.spike_lab.posts.application.mapper;

import com.moisaas.spike_lab.posts.application.dtos.CreatePostDto;
import com.moisaas.spike_lab.posts.application.dtos.PostDto;
import com.moisaas.spike_lab.posts.domain.entities.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    public PostEntity toEntity(CreatePostDto dto) {
        PostEntity entity = new PostEntity();

        entity.setTitle(dto.getTitle());
        entity.setBody(dto.getBody());

        return entity;
    }

    public PostDto toDto(PostEntity entity) {
        return PostDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .body(entity.getBody())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}