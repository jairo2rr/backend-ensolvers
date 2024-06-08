package com.jairo.dev.backend.model.mapper;

import com.jairo.dev.backend.model.Note;
import com.jairo.dev.backend.model.request.NoteRequestDTO;

public class NoteMapper {

    public static Note toEntity(NoteRequestDTO noteRequestDTO) {
        return Note.builder()
                .title(noteRequestDTO.getTitle())
                .content(noteRequestDTO.getContent()).build();
    }
}
