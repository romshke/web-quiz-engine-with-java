package engine.controller;

import engine.dto.QuizRequest;
import engine.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping(path = "/api/quizzes")
    public ResponseEntity<?> addQuiz(@RequestBody QuizRequest quizRequest) {
        return ResponseEntity.ok(quizService.saveQuiz(quizRequest));
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable int id) {
        return ResponseEntity.ok(quizService.getQuiz(id));
    }

    @GetMapping(path = "/api/quizzes")
    public ResponseEntity<?> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
    public ResponseEntity<?> solveQuiz(@PathVariable int id, @RequestParam(name = "answer") int index) {
        return ResponseEntity.ok(quizService.solveQuiz(id, index));
    }
}
