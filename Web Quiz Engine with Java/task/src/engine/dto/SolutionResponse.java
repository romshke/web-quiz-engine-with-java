package engine.dto;

public record SolutionResponse(
        boolean success,
        String feedback
) {}