package com.moisaas.the_moments.tags.infrastructure.http;

import com.moisaas.the_moments.shared.application.dtos.PaginationMultipleResponse;
import com.moisaas.the_moments.tags.application.dtos.FindAllTagDto;
import com.moisaas.the_moments.tags.application.dtos.TagDto;
import com.moisaas.the_moments.tags.application.usecases.FindAllTagUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final FindAllTagUseCase findAllTagUseCase;

    @GetMapping
    public ResponseEntity<PaginationMultipleResponse<TagDto>> listAll(@Valid FindAllTagDto params) {
        return ResponseEntity.status(HttpStatus.OK).body(new PaginationMultipleResponse<>(findAllTagUseCase.execute(params)));
    }
}
