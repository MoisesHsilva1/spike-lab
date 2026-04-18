package com.moisaas.the_moments.posts.application.mapper;

import com.moisaas.the_moments.posts.application.dtos.CreatePostDto;
import com.moisaas.the_moments.posts.application.dtos.PostDto;
import com.moisaas.the_moments.posts.domain.entities.PostEntity;
import com.moisaas.the_moments.tags.application.dtos.TagDto;
import com.moisaas.the_moments.tags.domain.entities.TagEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class PostMapper {
    public PostEntity toEntity(CreatePostDto dto, String imageUrl) {
        PostEntity entity = new PostEntity();

        entity.setTitle(dto.getTitle());
        entity.setBody(dto.getBody());
        entity.setImageUrl(imageUrl);
        entity.setStars(dto.getStars());
        entity.setTags(toTagEntities(dto.getTags()));

        return entity;
    }

    public PostDto toDto(PostEntity entity) {
        return PostDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .body(entity.getBody())
                .imageUrl(entity.getImageUrl())
                .stars(entity.getStars())
                .tags(toTagDtos(entity.getTags()))
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private List<TagEntity> toTagEntities(List<TagDto> tagDtos) {
        if (tagDtos == null) return Collections.emptyList();

        return tagDtos.stream()
                .map(dto -> {
                    TagEntity tag = new TagEntity();
                    tag.setId(dto.getId());
                    tag.setName(dto.getName());
                    return tag;
                })
                .toList();
    }

    private List<TagDto> toTagDtos(List<TagEntity> tags) {
        if (tags == null) return Collections.emptyList();

        return tags.stream()
                .map(t -> new TagDto(t.getId(), t.getName()))
                .toList();
    }
}
