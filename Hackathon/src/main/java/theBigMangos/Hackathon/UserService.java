package theBigMangos.Hackathon;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final UserRepo repo;
    private static final AtomicLong idCounter = new AtomicLong();

    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    public User createUser(String username) {
        User user = new User();
        user.setId(idCounter.incrementAndGet());
        user.setUsername(username);
        return repo.save(user);
    }

    public User getUser(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }

    public void saveUser(User user) {
        repo.save(user);
    }
}
