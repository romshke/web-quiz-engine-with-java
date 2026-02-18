package engine.service;

import engine.dto.QuizRequest;
import engine.dto.QuizResponse;
import engine.dto.SolutionResponse;
import engine.model.Quiz;
import engine.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final AtomicInteger idCounter;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
        this.idCounter = new AtomicInteger(1);
    }

    public QuizResponse saveQuiz(QuizRequest quizRequest) {
        Quiz quiz = new Quiz(
                idCounter.getAndIncrement(),
                quizRequest.title(),
                quizRequest.text(),
                quizRequest.options(),
                quizRequest.answer()
        );

        quizRepository.save(quiz);

        return convertToQuizResponse(quiz);
    }

    public QuizResponse getQuiz(int id) {
        return quizRepository.findById(id)
                .map(this::convertToQuizResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Quiz with id %d not found", id))
                );

    }

    public List<QuizResponse> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();

        return quizzes.stream()
                .map(this::convertToQuizResponse)
                .toList();
    }

    public SolutionResponse solveQuiz(int id, int answer) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Quiz with id %d not found", id))
                );

        if (quiz.getAnswer() == answer) {
            return correctSolution;
        } else {
            return incorrectSolution;
        }
    }

    private QuizResponse convertToQuizResponse(Quiz quiz) {
        return new QuizResponse(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getText(),
                quiz.getOptions()
        );
    }

    private final SolutionResponse correctSolution =
            new SolutionResponse(true, "Congratulations, you're right!");
    private final SolutionResponse incorrectSolution =
            new SolutionResponse(false, "Wrong answer! Please, try again.");
}
