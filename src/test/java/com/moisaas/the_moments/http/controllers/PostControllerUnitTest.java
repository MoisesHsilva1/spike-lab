package com.moisaas.the_moments.http.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerUnitTest {
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
    void shouldCreatePostSuccessfully() throws Exception {
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
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.body").value("Test Body"))
                .andExpect(jsonPath("$.stars").value(5.0));

        verify(createPostUseCase).execute(any(CreatePostDto.class), any());
    }

    @Test
    void shouldReturnBadRequestWhenPostDataIsInvalid() throws Exception {
        CreatePostDto invalidDto = CreatePostDto.builder()
                .title("")
                .body("Test Body")
                .stars(5.0)
                .build();

        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image content".getBytes());
        MockMultipartFile postPart = new MockMultipartFile("post", "", "application/json", objectMapper.writeValueAsString(invalidDto).getBytes());

        PostDto responseDto = PostDto.builder()
                .id("1")
                .title("")
                .body("Test Body")
                .imageUrl("http://example.com/image.jpg")
                .stars(5.0)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
        when(createPostUseCase.execute(any(CreatePostDto.class), any())).thenReturn(responseDto);

        mockMvc.perform(multipart("/api/v1/posts")
                        .file(image)
                        .file(postPart))
                .andExpect(status().isCreated());

        verify(createPostUseCase).execute(any(CreatePostDto.class), any());
    }

    @Test
    void shouldReturnBadRequestWhenImageIsMissing() throws Exception {
        CreatePostDto createDto = CreatePostDto.builder()
                .title("Test Title")
                .body("Test Body")
                .stars(5.0)
                .build();

        MockMultipartFile postPart = new MockMultipartFile("post", "", "application/json", objectMapper.writeValueAsString(createDto).getBytes());

        mockMvc.perform(multipart("/api/v1/posts")
                        .file(postPart))
                .andExpect(status().isBadRequest());

        verify(createPostUseCase, never()).execute(any(), any());
    }

    @Test
    void shouldReturnBadRequestWhenPostDataIsMissing() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image content".getBytes());

        mockMvc.perform(multipart("/api/v1/posts")
                        .file(image))
                .andExpect(status().isBadRequest());

        verify(createPostUseCase, never()).execute(any(), any());
    }

    @Test
    void shouldListAllPostsSuccessfully() throws Exception {
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
    void shouldListAllPostsWithEmptyResult() throws Exception {
        Page<PostDto> emptyPage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
        when(findAllPostUseCase.execute(any(FindAllPostDto.class))).thenReturn(emptyPage);

        mockMvc.perform(get("/api/v1/posts")
                        .param("offset", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.rows").isEmpty());

        verify(findAllPostUseCase).execute(any(FindAllPostDto.class));
    }

    @Test
    void shouldFindPostSuccessfully() throws Exception {
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
    void shouldReturnNotFoundWhenPostDoesNotExist() throws Exception {
        when(findPostUseCase.execute("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/posts/999"))
                .andExpect(status().isNotFound());

        verify(findPostUseCase).execute("999");
    }

    @Test
    void shouldDeletePostSuccessfully() throws Exception {
        doNothing().when(deletePostUseCase).execute("1");

        mockMvc.perform(delete("/api/v1/posts/1"))
                .andExpect(status().isNoContent());

        verify(deletePostUseCase).execute("1");
    }
}
