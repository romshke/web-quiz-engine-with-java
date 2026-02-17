package engine.controller;

import engine.dto.FeedbackDto;
import engine.dto.QuizDto;
import engine.service.QuizService;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping(path = "/api/quiz")
    public QuizDto showQuiz() {
        return quizService.getQuiz();
    }

    @PostMapping(path = "/api/quiz")
    public FeedbackDto saveQuiz(@RequestParam int answer) {
        return quizService.checkAnswer(answer);
    }
}
