package com.training.tutorials.service;

import com.training.tutorials.controller.model.TutorialDto;
import com.training.tutorials.repository.TutorialRepository;
import com.training.tutorials.repository.entity.Tutorial;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TutorialServiceTest {

    @InjectMocks
    private TutorialService service;

    @Mock
    private TutorialRepository repository;

    private Long getRandomLong() {
        return new Random().longs(1, 10).findFirst().getAsLong();
    }

    @Test
    void should_find_and_return_one_tutorial() throws Exception {
        // Arrange
        Long tutorialId = 1L;
        final Tutorial tutorialFetched = new Tutorial();
        tutorialFetched.setId(tutorialId);
        when(repository.findById(1L)).thenReturn(Optional.of(tutorialFetched));

        // Act
        Tutorial tuto = service.fetchById(tutorialId);

        // Assert
        assertThat(tuto).usingRecursiveComparison().isEqualTo(tutorialFetched);
        verify(repository, times(1)).findById(1L);
        verifyNoMoreInteractions(repository);

    }

    @Test
    void should_not_found_a_tutorial_that_doesnt_exists() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> service.fetchById(getRandomLong()));
        verify(repository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_find_and_return_all_tutorial() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(new Tutorial(), new Tutorial()));

        // Act & Assert
        assertThat(service.fetchAll()).hasSize(2);
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_save_one_tutorial() {
        // Arrange
        final Tutorial tutorialToSave = new Tutorial();
        when(repository.save(any(Tutorial.class))).thenReturn(tutorialToSave);

        // Act
        service.save(new TutorialDto());

        // Assert
        verify(repository, times(1)).save(any(Tutorial.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_delete_one_tutorial() {
        // Arrange
        doNothing().when(repository).deleteById(anyLong());

        // Act & Assert
        service.remove(getRandomLong());
        verify(repository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(repository);
    }


}
