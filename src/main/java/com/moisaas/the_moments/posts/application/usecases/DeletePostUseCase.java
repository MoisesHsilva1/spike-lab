package com.moisaas.the_moments.posts.application.usecases;

import com.moisaas.the_moments.posts.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePostUseCase {
    private final PostRepository postsRepository;
    private final FindPostUseCase findPostsUseCase;

    public void execute(String id)  {
        findPostsUseCase.execute(id).orElseThrow();

        postsRepository.deleteById(id);
    }
}
