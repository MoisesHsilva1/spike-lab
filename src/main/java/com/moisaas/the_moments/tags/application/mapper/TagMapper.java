package com.moisaas.the_moments.tags.application.mapper;

import com.moisaas.the_moments.tags.application.dtos.TagDto;
import com.moisaas.the_moments.tags.domain.entities.TagEntity;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public TagDto toDto(TagEntity entity) {
        return TagDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
