package com.moisaas.the_moments.posts.application.usecases;

import com.moisaas.the_moments.posts.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DeletePostUseCase {
    private final PostRepository postsRepository;
    private final FindPostUseCase findPostsUseCase;

    public void execute(String id) throws IOException {
        findPostsUseCase.execute(id).orElseThrow();

        postsRepository.deleteById(id);
    }
}
