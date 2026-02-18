package engine.repository;

import engine.model.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {

    void save(Quiz quiz);
    Optional<Quiz> findById(int id);
    List<Quiz> findAll();
}
