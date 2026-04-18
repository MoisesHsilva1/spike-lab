package com.moisaas.the_moments.http.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moisaas.the_moments.posts.application.dtos.CreatePostDto;
import com.moisaas.the_moments.posts.application.dtos.FindAllPostDto;
import com.moisaas.the_moments.posts.application.dtos.PostDto;
import com.moisaas.the_moments.posts.application.usecases.CreatePostUseCase;
import com.moisaas.the_moments.posts.application.usecases.DeletePostUseCase;
import com.moisaas.the_moments.posts.application.usecases.FindAllPostUseCase;
import com.moisaas.the_moments.posts.application.usecases.FindPostUseCase;
import com.moisaas.the_moments.posts.infrastructure.http.PostController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreatePostUseCase createPostUseCase;

    @MockBean
    private FindAllPostUseCase findAllPostUseCase;

    @MockBean
    private FindPostUseCase findPostUseCase;

    @MockBean
    private DeletePostUseCase deletePostUseCase;

    @Test
    void shouldCreatePost() throws Exception {
        CreatePostDto createDto = CreatePostDto.builder()
                .title("Test Title")
                .body("Test Body")
                .stars(5.0)
                .build();
        PostDto responseDto = PostDto.builder()
                .id("1")
                .title("Test Title")
                .body("Test Body")
                .imageUrl("http://example.com/image.jpg")
                .stars(5.0)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
        when(createPostUseCase.execute(any(CreatePostDto.class), any())).thenReturn(responseDto);

        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image content".getBytes());
        MockMultipartFile postPart = new MockMultipartFile("post", "", "application/json", objectMapper.writeValueAsString(createDto).getBytes());

        mockMvc.perform(multipart("/api/v1/posts")
                .file(image)
                .file(postPart))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Title"));

        verify(createPostUseCase).execute(any(CreatePostDto.class), any());
    }

    @Test
    void shouldListAllPosts() throws Exception {
        PostDto postDto = PostDto.builder()
                .id("1")
                .title("Test Title")
                .body("Test Body")
                .imageUrl("http://example.com/image.jpg")
                .stars(5.0)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
        Page<PostDto> page = new PageImpl<>(List.of(postDto), PageRequest.of(0, 10), 1);
        when(findAllPostUseCase.execute(any(FindAllPostDto.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/posts")
                .param("offset", "0")
                .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.rows[0].id").value("1"))
                .andExpect(jsonPath("$.rows[0].title").value("Test Title"));

        verify(findAllPostUseCase).execute(any(FindAllPostDto.class));
    }

    @Test
    void shouldFindPost() throws Exception {
        PostDto postDto = PostDto.builder()
                .id("1")
                .title("Test Title")
                .body("Test Body")
                .imageUrl("http://example.com/image.jpg")
                .stars(5.0)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
        when(findPostUseCase.execute("1")).thenReturn(Optional.of(postDto));

        mockMvc.perform(get("/api/v1/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.row.id").value("1"))
                .andExpect(jsonPath("$.row.title").value("Test Title"));

        verify(findPostUseCase).execute("1");
    }

    @Test
    void shouldReturnNotFoundWhenPostNotExists() throws Exception {
        when(findPostUseCase.execute("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/posts/1"))
                .andExpect(status().isNotFound());

        verify(findPostUseCase).execute("1");
    }

    @Test
    void shouldDeletePost() throws Exception {
        doNothing().when(deletePostUseCase).execute("1");

        mockMvc.perform(delete("/api/v1/posts/1"))
                .andExpect(status().isNoContent());

        verify(deletePostUseCase).execute("1");
    }
}
