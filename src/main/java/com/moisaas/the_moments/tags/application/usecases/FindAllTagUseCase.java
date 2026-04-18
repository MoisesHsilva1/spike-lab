package com.moisaas.the_moments.tags.application.usecases;


import com.moisaas.the_moments.tags.application.dtos.FindAllTagDto;
import com.moisaas.the_moments.tags.application.dtos.TagDto;
import com.moisaas.the_moments.tags.application.mapper.TagMapper;
import com.moisaas.the_moments.tags.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAllTagUseCase {
    private final TagRepository repository;
    private final TagMapper tagMapper;

    public Page<TagDto> execute(FindAllTagDto params) {
        Pageable pageable = PageRequest.of(
                params.getOffset(),
                params.getLimit()
        );

        return repository.findAll(pageable).map(tagMapper::toDto);
    }
}
