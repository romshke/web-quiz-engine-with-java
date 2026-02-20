package engine.service;

import engine.dto.QuizRequest;
import engine.dto.QuizResponse;
import engine.dto.SolveRequest;
import engine.dto.SolveResponse;
import engine.model.Quiz;
import engine.repository.QuizRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuizService {

    private static final Logger log = LoggerFactory.getLogger(QuizService.class);
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
                this.sortAnswers(quizRequest.answer())
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

    public SolveResponse solveQuiz(int id, SolveRequest solveRequest) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Quiz with id %d not found", id))
                );

        List<Integer> answersList = this.sortAnswers(solveRequest.answer());

        log.info("{}", answersList);

        if (quiz.getAnswer().size() == answersList.size()) {
            for (int i = 0; i < quiz.getAnswer().size(); i++) {
                if (quiz.getAnswer().get(i).intValue() != answersList.get(i).intValue()) {
                    return incorrectSolution;
                }
            }

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

    private final SolveResponse correctSolution =
            new SolveResponse(true, "Congratulations, you're right!");
    private final SolveResponse incorrectSolution =
            new SolveResponse(false, "Wrong answer! Please, try again.");

    private List<Integer> sortAnswers(List<Integer> answersList) {
        return answersList.stream().sorted(Comparator.naturalOrder()).toList();
    }
}
