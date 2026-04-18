package com.moisaas.the_moments.posts.infrastructure.http;

import com.moisaas.the_moments.posts.application.dtos.CreatePostDto;
import com.moisaas.the_moments.posts.application.dtos.FindAllPostDto;
import com.moisaas.the_moments.posts.application.dtos.PostDto;
import com.moisaas.the_moments.posts.application.usecases.CreatePostUseCase;
import com.moisaas.the_moments.posts.application.usecases.DeletePostUseCase;
import com.moisaas.the_moments.posts.application.usecases.FindAllPostUseCase;
import com.moisaas.the_moments.posts.application.usecases.FindPostUseCase;
import com.moisaas.the_moments.shared.application.dtos.PaginationMultipleResponse;
import com.moisaas.the_moments.shared.application.dtos.PaginationSingleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final CreatePostUseCase createPostUseCase;
    private final FindAllPostUseCase listAllPostUseCase;
    private final FindPostUseCase findPostUseCase;
    private final DeletePostUseCase deletePostUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto create(@RequestPart("post") @Valid CreatePostDto postDto, @RequestPart("image") MultipartFile image) throws IOException {
        return createPostUseCase.execute(postDto, image);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationMultipleResponse<PostDto> listAll(@Valid FindAllPostDto params) throws IOException {
        return new PaginationMultipleResponse<>(listAllPostUseCase.execute(params));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaginationSingleResponse<PostDto>> listOne(@PathVariable String id) {
        return findPostUseCase.execute(id).map(post -> ResponseEntity.ok(new PaginationSingleResponse<>(post))).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) throws IOException {
        deletePostUseCase.execute(id);
    }
}