package theBigMangos.Hackathon;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


/**
 * The service for the user
 * interacts with each user given the command from the repo
 */
@Service
public class UserService {
    private final UserRepo repo;
    private static final AtomicLong idCounter = new AtomicLong();


    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    public Set<Long> getUserFriends(Long userId) {
        return repo.findById(userId)
                .map(User::getFriends)
                .orElse(Collections.emptySet());
    }
    public User createUser(String username) {
        User user = new User();
        user.setId(idCounter.incrementAndGet());
        user.setUsername(username);
        return repo.save(user);
    }

    public Optional<User> getUser(Long id) {
        return repo.findById(id);
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
