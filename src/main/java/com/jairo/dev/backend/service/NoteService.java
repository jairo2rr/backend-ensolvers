package com.jairo.dev.backend.service;

import com.jairo.dev.backend.model.Category;
import com.jairo.dev.backend.model.Note;
import com.jairo.dev.backend.model.mapper.NoteMapper;
import com.jairo.dev.backend.model.request.NoteRequestDTO;
import com.jairo.dev.backend.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final CategoryService categoryService;

    public List<Note> findAll() {
        return (List<Note>) noteRepository.findAll();
    }

    @Transactional
    public Note create(NoteRequestDTO noteRequestDTO){
        Set<Category> categories = new HashSet<>();
        Note note = NoteMapper.toEntity(noteRequestDTO);
        for(Long categoryId : noteRequestDTO.getCategoryIds()){
            categories.add(categoryService.findById(categoryId));
        }
        note.setCategories(categories);
        LocalDateTime currentDate = LocalDateTime.now();
        note.setCreatedAt(currentDate);
        note.setIsArchived(false);
        return noteRepository.save(note);
    }

    public Note findById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    public Note update(Note note){
        return noteRepository.save(note);
    }

    public void delete(Long id){
        noteRepository.deleteById(id);
    }

    public List<Note> getArchivedNotes(){
        return noteRepository.findByIsArchivedTrue();
    }

    public List<Note> getUnarchivedNotes(){
        return noteRepository.findByIsArchivedFalse();
    }

    public void archiveNoteById(Long id){
        Note note = noteRepository.findById(id).orElseThrow();
        note.setIsArchived(true);
        noteRepository.save(note);
    }

    public void unarchiveNoteById(Long id){
        Note note = noteRepository.findById(id).orElseThrow();
        note.setIsArchived(false);
        noteRepository.save(note);
    }

    public List<Note> getNotesByCategories(Set<Category> categories){
        return noteRepository.findByCategoriesIn(categories);
    }
}
