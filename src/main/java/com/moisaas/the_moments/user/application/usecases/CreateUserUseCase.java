package com.moisaas.the_moments.user.application.usecases;


import com.moisaas.the_moments.user.application.dtos.CreateUserDto;
import com.moisaas.the_moments.user.application.dtos.UserDto;
import com.moisaas.the_moments.user.application.mapper.UserMapper;
import com.moisaas.the_moments.user.domain.entities.UserEntity;
import com.moisaas.the_moments.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserUseCase {
    private final UserRepository repository;
    private  final UserMapper mapper;

    @Transactional
    public UserDto execute(CreateUserDto user) {
        UserEntity entity = mapper.toEntity(user);

        return mapper.toDto(repository.save(entity));
    }

}
