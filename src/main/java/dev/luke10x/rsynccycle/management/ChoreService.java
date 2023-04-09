package dev.luke10x.rsynccycle.management;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChoreService {
    private final ChoreRepository choreRepository;

    public Iterable<Chore> getAllChores() {
        return choreRepository.findAll();
    }

    public void saveChore(Chore chore) {
        choreRepository.save(chore);
    }

    public Optional<Chore> getChoreById(Long id) {
        return choreRepository.findById(id);
    }

    public void deleteChoreById(Long id) {
        choreRepository.deleteById(id);
    }
}
