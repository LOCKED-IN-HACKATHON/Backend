package theBigMangos.Hackathon;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {
    private final UserRepo repo;

    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    public User createUser(User user) {
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

    public void updateUser(User user) {
        repo.save(user);
    }
}
