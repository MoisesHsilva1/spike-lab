package com.moisaas.the_moments.posts.application.usecases;

import com.moisaas.the_moments.posts.application.dtos.FindAllPostDto;
import com.moisaas.the_moments.posts.application.dtos.PostDto;
import com.moisaas.the_moments.posts.application.mapper.PostMapper;
import com.moisaas.the_moments.posts.domain.entities.PostEntity;
import com.moisaas.the_moments.posts.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAllPostUseCase {
    private final PostRepository postsRepository;
    private final PostMapper postMapper;

    public Page<PostDto> execute(FindAllPostDto params) {
        Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit());

        return fetchPosts(params, pageable).map(postMapper::toDto);
    }

    private Page<PostEntity> fetchPosts(FindAllPostDto params, Pageable pageable) {
        return (params.getStars() != null)
                ? postsRepository.findAllByStars(params.getStars(), pageable)
                : postsRepository.findAll(pageable);
    }
}