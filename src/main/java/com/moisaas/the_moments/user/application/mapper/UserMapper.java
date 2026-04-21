package com.moisaas.the_moments.user.application.mapper;

import com.moisaas.the_moments.user.application.dtos.CreateUserDto;
import com.moisaas.the_moments.user.application.dtos.UserDto;
import com.moisaas.the_moments.user.domain.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public UserEntity toEntity(CreateUserDto dto) {
        UserEntity entity = new UserEntity();

        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setName(dto.getName());

        return entity;
    }

    public UserDto toDto(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
