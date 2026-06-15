package com.moisaas.spike_lab.posts.infrastructure.http;

import com.moisaas.spike_lab.posts.application.dtos.CreatePostDto;
import com.moisaas.spike_lab.posts.application.dtos.FindAllPostDto;
import com.moisaas.spike_lab.posts.application.dtos.PostDto;
import com.moisaas.spike_lab.posts.application.usecases.CreatePostUseCase;
import com.moisaas.spike_lab.posts.application.usecases.FindAllPostUseCase;
import com.moisaas.spike_lab.shared.application.dtos.PaginationMultipleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final CreatePostUseCase createPostUseCase;
    private final FindAllPostUseCase listAllPostUseCase;

    @PostMapping()
    public ResponseEntity<PostDto> create(@RequestBody @Valid CreatePostDto postDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createPostUseCase.execute(postDto));
    }

    @GetMapping
    public ResponseEntity<PaginationMultipleResponse<PostDto>> listAll(@Valid FindAllPostDto params) {
        return ResponseEntity.status(HttpStatus.OK).body(new PaginationMultipleResponse<>(listAllPostUseCase.execute(params)));
    }
}