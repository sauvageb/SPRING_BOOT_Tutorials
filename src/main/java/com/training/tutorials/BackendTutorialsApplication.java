package com.training.tutorials;

import com.training.tutorials.repository.TutorialRepository;
import com.training.tutorials.repository.entity.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BackendTutorialsApplication implements CommandLineRunner {

    private final TutorialRepository tutorialRepository;

    public BackendTutorialsApplication(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendTutorialsApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        List<Tutorial> tutos = new ArrayList<>();
        tutos.add(new Tutorial("Tuto #1", "Description #1"));
        tutos.add(new Tutorial("Tuto #2", "Description #2", true));
        tutos.add(new Tutorial("Tuto #3", "Description #3"));
        tutos.add(new Tutorial("Tuto #4", "Description #4"));

        tutorialRepository.saveAll(tutos);
    }
}
