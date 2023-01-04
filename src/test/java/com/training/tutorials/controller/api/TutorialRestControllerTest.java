package com.training.tutorials.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.tutorials.repository.TutorialRepository;
import com.training.tutorials.repository.entity.Tutorial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TutorialRestController.class)
class TutorialRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TutorialRepository tutorialRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void when_getAllTutorials__then__returnAllTutorials() throws Exception {
        // given
        List<Tutorial> tutorials = new ArrayList<>(
                Arrays.asList(new Tutorial("Spring Boot @WebMvcTest 1", "Description 1", true),
                        new Tutorial("Spring Boot @WebMvcTest 2", "Description 2", true),
                        new Tutorial("Spring Boot @WebMvcTest 3", "Description 3", true)));

        // when
        when(tutorialRepository.findAll()).thenReturn(tutorials);

        // then
        mockMvc.perform(get("/api/tutorials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(tutorials.size()))
                .andDo(print());
    }

    @Test
    void when_getAllTutorialsWithSeachedTitle__then__returnAllTutorialsContainingSearchedTitle() throws Exception {
        // given
        String searchedTitle = "Spring Boot @WebMvcTest";
        List<Tutorial> tutorials = new ArrayList<>(
                Arrays.asList(new Tutorial("Spring Boot @WebMvcTest", "Description 1", true)
                ));

        // when
        when(tutorialRepository.findByTitleContaining(searchedTitle)).thenReturn(tutorials);

        // then
        mockMvc.perform(get("/api/tutorials").param("title", searchedTitle))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(tutorials.size()))
                .andDo(print());
    }

    @Test
    void when_getAllTutorialsWithUnknownSeachedTitle__then__http_204() throws Exception {
        // given
        String searchedTitle = "Unknowned Title";
        List<Tutorial> tutorials = new ArrayList<>(
                Arrays.asList(new Tutorial("Spring Boot @WebMvcTest", "Description 1", true)
                ));

        // when
        when(tutorialRepository.findByTitleContaining(searchedTitle)).thenReturn(tutorials);

        // then
        mockMvc.perform(get("/api/tutorials").param("title", searchedTitle))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(tutorials.size()))
                .andDo(print());
    }

    @Test
    void when_getTutorialById__then__returnSpecificTutorial() throws Exception {
        // given
        Long id = 1L;
        // when
        Tutorial tutorial = new Tutorial("Tuto #1", "Description #1");
        tutorial.setId(id);
        when(tutorialRepository.findById(id)).thenReturn(Optional.of(tutorial));

        // then
        mockMvc.perform(get("/api/tutorials/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(tutorial.getTitle()))
                .andExpect(jsonPath("$.description").value(tutorial.getDescription()))
                .andExpect(jsonPath("$.published").value(tutorial.isPublished()))
                .andDo(print());
    }

    @Test
    void when_getTutorialById__then__returnNotFound() throws Exception {
        // given
        Long id = 100L;
        // when
        when(tutorialRepository.findById(100L)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(get("/api/tutorials/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    void createTutorial__then__return_http_201_with_created_status() throws Exception {
        Tutorial tutorial = new Tutorial("Created Tuto", "The final description", true);

        mockMvc.perform(post("/api/tutorials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tutorial)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void updateTutorial() throws Exception {
        long id = 1L;
        Tutorial tutorial = new Tutorial("Created Tuto", "The final description", true);
        tutorial.setId(id);

        Tutorial updatedtutorial = new Tutorial("Updated Tuto", "The final description 2.0", true);
        updatedtutorial.setId(id);

        when(tutorialRepository.findById(id)).thenReturn(Optional.of(tutorial));
        when(tutorialRepository.save(any(Tutorial.class))).thenReturn(updatedtutorial);

        mockMvc.perform(put("/api/tutorials/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedtutorial)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedtutorial.getTitle()))
                .andExpect(jsonPath("$.description").value(updatedtutorial.getDescription()))
                .andExpect(jsonPath("$.published").value(updatedtutorial.isPublished()))
                .andDo(print());
    }

    @Test
    void deleteTutorialById__then__return_http_200() throws Exception {
        Long id = 1L;
        Tutorial tutorial = new Tutorial("Tuto #22", "Description #22");
        tutorial.setId(id);

        when(tutorialRepository.findById(id)).thenReturn(Optional.of(tutorial));
        doNothing().when(tutorialRepository).deleteById(id);

        mockMvc.perform(delete("/api/tutorials/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteTutorials__then__return_http_200() throws Exception {
        doNothing().when(tutorialRepository).deleteAll();

        mockMvc.perform(delete("/api/tutorials"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
