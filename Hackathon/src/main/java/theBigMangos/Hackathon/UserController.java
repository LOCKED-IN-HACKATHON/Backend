package theBigMangos.Hackathon;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/users")
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

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return service.getUser(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found")));
    }


    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        return service.getUser(1234L)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/me/friends")
    public ResponseEntity<Set<User>> getCurrentUserFriends() {
        Set<Long> friendsId = service.getUserFriends(1234L);
        Set<User> friends = new HashSet<>();
        for (Long id : friendsId) {
            service.getUser(id).ifPresent(friends::add);
        }
        return ResponseEntity.ok(Collections.unmodifiableSet(friends));
    }

}

