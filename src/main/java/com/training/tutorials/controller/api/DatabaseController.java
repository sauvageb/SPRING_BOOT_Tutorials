package com.training.tutorials.controller.api;

import com.training.tutorials.repository.entity.Tutorial;
import com.training.tutorials.repository.TutorialRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api")
@RestController
public class DatabaseController {

    private TutorialRepository tutorialRepository;

    public DatabaseController(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @GetMapping("/populate")
    public ResponseEntity<?> populateDatabase() {
        List<Tutorial> tutos = new ArrayList<>();
        tutos.add(new Tutorial("Tuto #1", "Description #1"));
        tutos.add(new Tutorial("Tuto #2", "Description #2", true));
        tutos.add(new Tutorial("Tuto #3", "Description #3"));
        tutos.add(new Tutorial("Tuto #4", "Description #4"));

        tutorialRepository.saveAll(tutos);

        return ResponseEntity.noContent().build();
    }
}
