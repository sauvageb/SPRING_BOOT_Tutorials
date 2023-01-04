package com.training.tutorials.controller.api;

import com.training.tutorials.repository.TutorialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExampleRestController.class)
class ExampleRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TutorialRepository tutorialRepository;

    @Test
    public void when__countWordLength__then_returnWordLength() throws Exception {
        String myWord = "Boris";
        mockMvc.perform(get("/api/count").param("word", myWord))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber())
                .andExpect(jsonPath("$").value(5L));

    }

    @Test
    public void given__empty_word_when__countWordLength__thenReturnWordLength() throws Exception {
        String myWord = "";
        mockMvc.perform(get("/api/count").param("word", myWord))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber())
                .andExpect(jsonPath("$").value(0));

    }

    @Test
    public void given__noParam_when__countWordLength__thenReturnWordLength() throws Exception {
        mockMvc.perform(get("/api/count"))
                .andExpect(status().isBadRequest());

    }


}
