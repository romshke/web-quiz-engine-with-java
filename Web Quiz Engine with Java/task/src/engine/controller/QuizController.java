package engine.controller;

import engine.dto.QuizRequest;
import engine.dto.SolveRequest;
import engine.service.QuizService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping(path = "/api/quizzes")
    public ResponseEntity<?> addQuiz(@Valid @RequestBody QuizRequest quizRequest) {
        return ResponseEntity.ok(quizService.saveQuiz(quizRequest));
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable @Min(1) int id) {
        return ResponseEntity.ok(quizService.getQuiz(id));
    }

    @GetMapping(path = "/api/quizzes")
    public ResponseEntity<?> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
    public ResponseEntity<?> solveQuiz(
            @PathVariable @Min(1) int id,
            @RequestBody SolveRequest solveRequest) {
        return ResponseEntity.ok(quizService.solveQuiz(id, solveRequest));
    }
}
