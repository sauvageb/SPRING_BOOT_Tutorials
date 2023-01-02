package com.training.tutorials.repository;

import com.training.tutorials.repository.entity.Tutorial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class TutorialRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TutorialRepository tutorialRepository;

    @Test
    void when_findByPublished_then_returnAllPublishedTutorials() {
        // given
        entityManager.persist(new Tutorial("Tuto #1", "Description #1"));
        entityManager.persist(new Tutorial("Tuto #2", "Description #2", true));

        // when
        List<Tutorial> result = tutorialRepository.findByPublished(true);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void when_findByTitleContaining_then_returnMatchingTutorials() {
        // given
        entityManager.persist(new Tutorial("Tuto #1234", "Description #1111"));
        Tutorial tuto = new Tutorial("Tuto #2345", "Description #2222", true);
        entityManager.persist(tuto);

        // when
        List<Tutorial> result = tutorialRepository.findByTitleContaining(tuto.getTitle());
        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo(tuto.getTitle());
        assertThat(result.get(0).getDescription()).isEqualTo(tuto.getDescription());
        assertThat(result.get(0).isPublished()).isEqualTo(true);
    }

}
