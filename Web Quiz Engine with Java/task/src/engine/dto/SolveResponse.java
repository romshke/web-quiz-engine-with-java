package engine.dto;

public record SolveResponse(
        boolean success,
        String feedback
) {}