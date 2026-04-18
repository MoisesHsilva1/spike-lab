package com.moisaas.the_moments.tags.infrastructure.http;

import com.moisaas.the_moments.shared.application.dtos.PaginationMultipleResponse;
import com.moisaas.the_moments.tags.application.dtos.FindAllTagDto;
import com.moisaas.the_moments.tags.application.dtos.TagDto;
import com.moisaas.the_moments.tags.application.usecases.FindAllTagUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final FindAllTagUseCase findAllTagUseCase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationMultipleResponse<TagDto> listAll(@Valid FindAllTagDto params) throws IOException {
        return new PaginationMultipleResponse<>(findAllTagUseCase.execute(params));
    }
}
