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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final CreatePostUseCase createPostUseCase;
    private final FindAllPostUseCase listAllPostUseCase;
    private final FindPostUseCase findPostUseCase;
    private final DeletePostUseCase deletePostUseCase;
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto> create(@RequestPart("post") @Valid CreatePostDto postDto, @RequestPart("image") MultipartFile image) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createPostUseCase.execute(postDto, image));
    }

    @GetMapping
    public ResponseEntity<PaginationMultipleResponse<PostDto>> listAll(@Valid FindAllPostDto params) {
        return ResponseEntity.status(HttpStatus.OK).body(new PaginationMultipleResponse<>(listAllPostUseCase.execute(params)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginationSingleResponse<PostDto>> listOne(@PathVariable String id) {
        return findPostUseCase.execute(id).map(post -> ResponseEntity.status(HttpStatus.OK).body(new PaginationSingleResponse<>(post))).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id)  {
        deletePostUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}