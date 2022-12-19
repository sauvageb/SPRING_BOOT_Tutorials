package com.training.tutorials.controller;

import com.training.tutorials.controller.model.TutorialDto;
import com.training.tutorials.repository.entity.Tutorial;
import com.training.tutorials.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class TutorialController {

    @Autowired
    TutorialService tutorialService;

    @GetMapping("/tutorials")
    public String displayAllTutorials(Model model) {
        try {
            List<Tutorial> tutorialList = tutorialService.fetchAll();
            model.addAttribute("tutorials", tutorialList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "tutorials";
    }

    @GetMapping("/tutorials/{id}")
    public String displayTutorial(@PathVariable Long id, Model model) {
        try {
            Tutorial t = tutorialService.fetchById(id);
            model.addAttribute("tutorial", t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "tutorial-details";
    }

    @GetMapping("/tutorials/add")
    public String addTutorialForm() {
        return "add-tutorial";
    }

    @PostMapping("/tutorials/add")
    public String addGameSubmission(TutorialDto tutorialDto) {
        tutorialService.save(tutorialDto);
        return "redirect:/tutorials";
    }

    @GetMapping("/tutorials/delete/{id}")
    public String deleteTutorial(@PathVariable Long id, Model model) {
        tutorialService.remove(id);
        return "redirect:/tutorials";
    }

}
