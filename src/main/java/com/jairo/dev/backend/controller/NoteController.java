package com.jairo.dev.backend.controller;

import com.jairo.dev.backend.model.Category;
import com.jairo.dev.backend.model.Note;
import com.jairo.dev.backend.model.request.NoteRequestDTO;
import com.jairo.dev.backend.service.CategoryService;
import com.jairo.dev.backend.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NoteController {

    private final NoteService noteService;
    private final CategoryService categoryService;

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.findAll());
    }

    @PostMapping("/note")
    public ResponseEntity<Note> createNote(@RequestBody NoteRequestDTO noteRequestDTO) {
        return new ResponseEntity<>(noteService.create(noteRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.findById(id));
    }

    @PutMapping("/note")
    public ResponseEntity<Note> updateNote(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.update(note));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/note/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteService.delete(id);
    }

    @GetMapping("/notes/archived")
    public ResponseEntity<List<Note>> getArchivedNotes() {
        return ResponseEntity.ok(noteService.getArchivedNotes());
    }

    @GetMapping("/notes/unarchived")
    public ResponseEntity<List<Note>> getUnarchivedNotes() {
        return ResponseEntity.ok(noteService.getUnarchivedNotes());
    }

    @PostMapping("/note/{id}/archive")
    public void archiveNoteById(@PathVariable Long id) {
        noteService.archiveNoteById(id);
    }

    @PostMapping("/note/{id}/unarchive")
    public void unarchiveNoteById(@PathVariable Long id) {
        noteService.unarchiveNoteById(id);
    }

    @GetMapping("/note/filter")
    public ResponseEntity<List<Note>> getNotesByCategories(@RequestParam Set<Long> categoryIds) {
        Set<Category> categories = categoryIds.stream()
                .map(categoryService::findById)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(noteService.getNotesByCategories(categories));
    }
}
