package com.training.tutorials.service;

import com.training.tutorials.controller.model.TutorialDto;
import com.training.tutorials.repository.TutorialRepository;
import com.training.tutorials.repository.entity.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    public Tutorial fetchById(Long id) throws Exception {
        Optional<Tutorial> tuto = tutorialRepository.findById(id);
        return tuto.orElseThrow(() -> new Exception());
    }

    public List<Tutorial> fetchAll() {
        return tutorialRepository.findAll();
    }

    public void save(TutorialDto dto) {
        Tutorial newTutorial = new Tutorial(dto.getTitle(), dto.getDescription(), false);
        tutorialRepository.save(newTutorial);
    }

    public void remove(Long id) {
        tutorialRepository.deleteById(id);
    }
}
