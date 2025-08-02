package theBigMangos.Hackathon;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestParam String username) {
        User user = service.createUser(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return service.getUser(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }
}

