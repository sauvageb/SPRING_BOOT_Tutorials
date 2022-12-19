package com.training.tutorials.controller.api;

import com.training.tutorials.controller.model.TutorialDto;
import com.training.tutorials.repository.TutorialRepository;
import com.training.tutorials.repository.entity.Tutorial;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api")
public class TutorialRestController {

    private TutorialRepository tutorialRepository;

    public TutorialRestController(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    //ROUTE :    /api/tutorials?title=Titre #3
    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(value = "title", required = false) String searchedTitle) {
        List<Tutorial> tutos = new ArrayList<>();

        if (searchedTitle == null) {
            tutorialRepository.findAll().forEach(t -> tutos.add(t));
        } else {
            tutorialRepository.findByTitleContaining(searchedTitle).forEach(t -> tutos.add(t));
        }

        if (tutos.isEmpty()) {
            // HTTP 204 : sans contenu
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            // HTTP 200 avec les tutos
            return ResponseEntity.status(HttpStatus.OK).body(tutos);
        }
    }

    // ROUTE : /api/tutorials/3
    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") Long tutorialId) {
        Optional<Tutorial> tutorial = tutorialRepository.findById(tutorialId);
        if (tutorial.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(tutorial.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished() {

        List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

        if (tutorials.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(tutorials);
        }
    }


    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody TutorialDto dto) {
        try {
            Tutorial newTutorial = new Tutorial(dto.getTitle(), dto.getDescription(), dto.isPublished());
            tutorialRepository.save(newTutorial);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTutorial);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") Long idTuto, @RequestBody TutorialDto dto) {

        Optional<Tutorial> tutorial = tutorialRepository.findById(idTuto);
        if (tutorial.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Tutorial updateTuto = tutorial.get();
        updateTuto.setTitle(dto.getTitle());
        updateTuto.setDescription(dto.getDescription());
        updateTuto.setPublished(dto.isPublished());
        tutorialRepository.save(updateTuto);
        return ResponseEntity.status(HttpStatus.OK).body(updateTuto);
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorialById(@PathVariable("id") Long idTuto) {
        Optional<Tutorial> tutorial = tutorialRepository.findById(idTuto);
        if (tutorial.isPresent()) {
            tutorialRepository.deleteById(idTuto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteTutorial() {
        try {
            tutorialRepository.deleteAll();
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
