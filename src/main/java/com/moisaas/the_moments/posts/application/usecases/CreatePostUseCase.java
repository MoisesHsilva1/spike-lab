package com.moisaas.the_moments.posts.application.usecases;

import com.moisaas.the_moments.posts.application.dtos.CreatePostDto;
import com.moisaas.the_moments.posts.application.dtos.PostDto;
import com.moisaas.the_moments.posts.application.mapper.PostMapper;
import com.moisaas.the_moments.posts.domain.entities.PostEntity;
import com.moisaas.the_moments.posts.domain.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePostUseCase {
    private final PostRepository postsRepository;
    private final PostMapper postMapper;

    @Transactional
    public PostDto execute(CreatePostDto post) {
        PostEntity entity = postMapper.toEntity(post);

        return postMapper.toDto(postsRepository.save(entity));
    }
}