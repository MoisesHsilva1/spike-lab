package com.moisaas.spike_lab.posts.application.usecases;

import com.moisaas.spike_lab.posts.application.dtos.PostDto;
import com.moisaas.spike_lab.posts.application.mapper.PostMapper;
import com.moisaas.spike_lab.posts.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindPostUseCase {
    private final PostRepository postsRepository;
    private final PostMapper postMapper;

    public Optional<PostDto> execute(String id) {
        return postsRepository.findById(id).map(postMapper::toDto);
    }
}
