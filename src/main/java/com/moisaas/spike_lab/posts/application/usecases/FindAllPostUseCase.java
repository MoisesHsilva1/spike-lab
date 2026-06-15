package com.moisaas.spike_lab.posts.application.usecases;

import com.moisaas.spike_lab.posts.application.dtos.FindAllPostDto;
import com.moisaas.spike_lab.posts.application.dtos.PostDto;
import com.moisaas.spike_lab.posts.application.mapper.PostMapper;
import com.moisaas.spike_lab.posts.domain.entities.PostEntity;
import com.moisaas.spike_lab.posts.domain.repository.PostRepository;
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

        return fetchPosts(pageable).map(postMapper::toDto);
    }

    private Page<PostEntity> fetchPosts(Pageable pageable) {
        return postsRepository.findAll(pageable);
    }
}