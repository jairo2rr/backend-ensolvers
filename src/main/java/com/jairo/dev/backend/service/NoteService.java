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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Note findById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Transactional
    public Note update(NoteRequestDTO noteDTO){
        Note note = noteRepository.findById(noteDTO.getId()).orElseThrow();
        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());
        Set<Category> categories = new HashSet<>();
        for(Long categoryId : noteDTO.getCategoryIds()){
            categories.add(categoryService.findById(categoryId));
        }
        return noteRepository.save(note);
    }

    @Transactional
    public void delete(Long id){
        noteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Note> getArchivedNotes(){
        return noteRepository.getAllByIsArchivedTrue();
    }

    @Transactional(readOnly = true)
    public List<Note> getUnarchivedNotes(){
        return noteRepository.getAllByIsArchivedFalse();
    }

    @Transactional
    public void archiveNoteById(Long id){
        Note note = noteRepository.findById(id).orElseThrow();
        note.setIsArchived(true);
        noteRepository.save(note);
    }

    @Transactional
    public void unarchiveNoteById(Long id){
        Note note = noteRepository.findById(id).orElseThrow();
        note.setIsArchived(false);
        noteRepository.save(note);
    }

    @Transactional(readOnly = true)
    public List<Note> getNotesByCategories(Set<Category> categories){
        return noteRepository.findByCategoriesIn(categories);
    }
}
