package com.jairo.dev.backend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class NoteRequestDTO {
    private String title;
    private String content;
    private List<Long> categoryIds;
}
