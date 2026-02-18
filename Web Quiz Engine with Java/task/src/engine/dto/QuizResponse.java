package engine.dto;

import java.util.List;

public record QuizResponse(
        int id,
        String title,
        String text,
        List<String> options
) {}
