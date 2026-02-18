package engine.repository;

import engine.model.Quiz;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MapQuizRepositoryImpl implements QuizRepository {
    private final Map<Integer, Quiz> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Quiz quiz) {
        storage.put(quiz.getId(), quiz);
    }

    @Override
    public Optional<Quiz> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Quiz> findAll() {
        return new ArrayList<>(storage.values());
    }
}
