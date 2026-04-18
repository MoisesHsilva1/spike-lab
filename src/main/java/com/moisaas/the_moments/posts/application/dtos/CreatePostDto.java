package com.moisaas.the_moments.posts.application.dtos;

import com.moisaas.the_moments.tags.application.dtos.TagDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDto {
    private String title;
    private String body;
    private MultipartFile image;
    private double stars;
    private List<TagDto> tags;
}