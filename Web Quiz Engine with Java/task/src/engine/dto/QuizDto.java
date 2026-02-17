package engine.dto;

import java.util.List;

public record QuizDto(
        String title,
        String text,
        List<String> options
) {}
