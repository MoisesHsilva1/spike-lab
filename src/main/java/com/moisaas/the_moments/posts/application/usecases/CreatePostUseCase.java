package com.moisaas.the_moments.posts.application.usecases;

import com.moisaas.the_moments.posts.application.dtos.CreatePostDto;
import com.moisaas.the_moments.posts.application.dtos.PostDto;
import com.moisaas.the_moments.posts.application.mapper.PostMapper;
import com.moisaas.the_moments.posts.domain.entities.PostEntity;
import com.moisaas.the_moments.posts.domain.repository.PostRepository;
import com.moisaas.the_moments.shared.infrastructure.services.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CreatePostUseCase {
    private final PostRepository postsRepository;
    private final PostMapper postMapper;
    private final FileStorageService fileStorageService;

    @Transactional
    public PostDto execute(CreatePostDto post, MultipartFile image) throws IOException {
        String imageKey = fileStorageService.uploadFile(image);
        PostEntity entity = postMapper.toEntity(post, imageKey);
        PostEntity saved = postsRepository.save(entity);
        PostDto response = postMapper.toDto(saved);

        response.setImageUrl(fileStorageService.getPresignedUrl(imageKey));

        return response;
    }
}