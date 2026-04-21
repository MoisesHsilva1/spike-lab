package com.moisaas.the_moments.user.infrastructure.http;

import com.moisaas.the_moments.user.application.dtos.CreateUserDto;
import com.moisaas.the_moments.user.application.dtos.UserDto;
import com.moisaas.the_moments.user.application.usecases.CreateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final CreateUserUseCase createUserUseCase;

    @PostMapping()
    public ResponseEntity<UserDto> create(@Valid @RequestBody CreateUserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserUseCase.execute(user));
    }
}
