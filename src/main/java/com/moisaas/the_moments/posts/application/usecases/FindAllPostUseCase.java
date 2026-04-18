package com.moisaas.the_moments.posts.application.usecases;

import com.moisaas.the_moments.posts.application.dtos.FindAllPostDto;
import com.moisaas.the_moments.posts.application.dtos.PostDto;
import com.moisaas.the_moments.posts.application.mapper.PostMapper;
import com.moisaas.the_moments.posts.domain.entities.PostEntity;
import com.moisaas.the_moments.posts.domain.repository.PostRepository;
import com.moisaas.the_moments.shared.infrastructure.services.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindAllPostUseCase {
    private final PostRepository postsRepository;
    private final PostMapper postMapper;
    private final FileStorageService fileStorageService;

    public Page<PostDto> execute(FindAllPostDto params) throws IOException {
        Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit());

        return fetchPosts(params, pageable).map(entity -> enrichPostWithPresignedUrl(entity));
    }

    private Page<PostEntity> fetchPosts(FindAllPostDto params, Pageable pageable) {
        return (params.getStars() != null)
                ? postsRepository.findAllByStars(params.getStars(), pageable)
                : postsRepository.findAll(pageable);
    }

    private PostDto enrichPostWithPresignedUrl(PostEntity entity) {
        PostDto dto = postMapper.toDto(entity);

        Optional.ofNullable(entity.getImageUrl())
                .filter(key -> !key.isBlank())
                .map(fileStorageService::getPresignedUrl)
                .ifPresent(dto::setImageUrl);

        return dto;
    }
}