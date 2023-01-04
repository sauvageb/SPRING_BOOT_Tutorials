package com.training.tutorials.controller.api;

import com.training.tutorials.repository.TutorialRepository;
import com.training.tutorials.repository.entity.Tutorial;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(DatabaseController.class)
class DatabaseControllerTest {

    // Formule pour la rédaction des tests d'acceptations
    // GIVEN : un contexte
    // WHEN : une action est réalisée
    // THEN : validation du résultat attendu

    @MockBean
    private TutorialRepository tutorialRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void when_populateDatabase__then_return_http_204() throws Exception {
        // given
        List<Tutorial> tutorialList = new ArrayList<>();
        tutorialList.add(new Tutorial("TITRE", "DESCRIPTION"));

        // when
        when(tutorialRepository.saveAll(tutorialList)).thenReturn(tutorialList);

        // then
        mockMvc.perform(get("/api/populate"))
                .andExpect(status().isNoContent())
                .andDo(print());

    }





}
