package com.training.tutorials.repository;

import com.training.tutorials.repository.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// JpaRepository use 2 generic types : Entity and Primary Key
@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

    //    @Query("SELECT t FROM Tutorial t WHERE t.published=:published")
    List<Tutorial> findByPublished(boolean published);

    List<Tutorial> findByTitleContaining(String title);

}
