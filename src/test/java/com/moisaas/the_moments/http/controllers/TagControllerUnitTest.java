package com.moisaas.the_moments.http.controllers;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moisaas.the_moments.tags.application.dtos.FindAllTagDto;
import com.moisaas.the_moments.tags.application.dtos.TagDto;
import com.moisaas.the_moments.tags.application.usecases.FindAllTagUseCase;
import com.moisaas.the_moments.tags.infrastructure.http.TagController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
class TagControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FindAllTagUseCase findAllTagUseCase;

    @Test
    void shouldFindAllTagsSuccessfully() throws Exception {
        TagDto tagDto = TagDto.builder()
                .id("1")
                .name("Technology")
                .build();
        Page<TagDto> page = new PageImpl<>(List.of(tagDto), PageRequest.of(0, 10), 1);
        when(findAllTagUseCase.execute(any(FindAllTagDto.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/tags")
                        .param("offset", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.rows[0].id").value("1"))
                .andExpect(jsonPath("$.rows[0].name").value("Technology"));

        verify(findAllTagUseCase).execute(any(FindAllTagDto.class));
    }

    @Test
    void shouldFindAllTagsWithEmptyResult() throws Exception {
        Page<TagDto> emptyPage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
        when(findAllTagUseCase.execute(any(FindAllTagDto.class))).thenReturn(emptyPage);

        mockMvc.perform(get("/api/v1/tags")
                        .param("offset", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.rows").isEmpty());

        verify(findAllTagUseCase).execute(any(FindAllTagDto.class));
    }

    @Test
    void shouldFindAllTagsWithMultipleResults() throws Exception {
        TagDto tag1 = TagDto.builder()
                .id("1")
                .name("Technology")
                .build();
        TagDto tag2 = TagDto.builder()
                .id("2")
                .name("Lifestyle")
                .build();
        Page<TagDto> page = new PageImpl<>(List.of(tag1, tag2), PageRequest.of(0, 10), 2);
        when(findAllTagUseCase.execute(any(FindAllTagDto.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/tags")
                        .param("offset", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.rows").isArray())
                .andExpect(jsonPath("$.rows.length()").value(2))
                .andExpect(jsonPath("$.rows[0].name").value("Technology"))
                .andExpect(jsonPath("$.rows[1].name").value("Lifestyle"));

        verify(findAllTagUseCase).execute(any(FindAllTagDto.class));
    }
}
