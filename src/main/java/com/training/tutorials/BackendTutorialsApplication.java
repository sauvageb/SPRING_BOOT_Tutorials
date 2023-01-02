package com.training.tutorials;

import com.training.tutorials.repository.TutorialRepository;
import com.training.tutorials.repository.entity.Tutorial;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BackendTutorialsApplication {

    private final TutorialRepository tutorialRepository;

    public BackendTutorialsApplication(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendTutorialsApplication.class, args);
    }


    @Bean
    @Profile("!test")
    CommandLineRunner commandLineRunner() {
        return args -> {
            List<Tutorial> tutos = new ArrayList<>();
            tutos.add(new Tutorial("Tuto #1", "Description #1"));
            tutos.add(new Tutorial("Tuto #2", "Description #2", true));
            tutos.add(new Tutorial("Tuto #3", "Description #3"));
            tutos.add(new Tutorial("Tuto #4", "Description #4"));

            tutorialRepository.saveAll(tutos);
        };

    }
}
