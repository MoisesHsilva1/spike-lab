package com.moisaas.the_moments.posts.application.usecases;

import com.moisaas.the_moments.posts.application.dtos.PostDto;
import com.moisaas.the_moments.posts.application.mapper.PostMapper;
import com.moisaas.the_moments.posts.domain.repository.PostRepository;
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
