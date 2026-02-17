package engine.service;

import engine.dto.FeedbackDto;
import engine.dto.QuizDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    public QuizDto getQuiz() {
        String title = "The Java Logo",
                text = "What is depicted on the Java logo?";
        List<String> options = List.of("Robot", "Tea leaf", "Cup of coffee", "Bug");

        return new QuizDto(title, text, options);
    }

    public FeedbackDto checkAnswer(int answer) {
        String feedbackCorrectAnswer = "Congratulations, you're right!",
                feedbackWrongAnswer = "Wrong answer! Please, try again.";

        if (answer == 2) {
            return new FeedbackDto(true, feedbackCorrectAnswer);
        } else {
            return new FeedbackDto(false, feedbackWrongAnswer);
        }
    }
}
