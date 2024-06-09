package com.jairo.dev.backend.repository;

import com.jairo.dev.backend.model.Category;
import com.jairo.dev.backend.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
    List<Note> getAllByIsArchivedFalse();
    List<Note> getAllByIsArchivedTrue();

    List<Note> findByCategoriesIn(Set<Category> categories);
}
