package theBigMangos.Hackathon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import theBigMangos.Hackathon.model.User;
import theBigMangos.Hackathon.service.UserService;

import java.util.*;

/**
 * User controller
 * many of the methods are preset to use the id "1234L"
 * remember to change this in the future
 *
 * this is what is used to interact with the front end
 */

@RestController
@RequestMapping("/main")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /**
     *  Called to create a new user, altough each field will need to be set
     *
     * @param username
     *
     * @return the user
     */
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestParam String username) {
        User user = service.createUser(username);
        return ResponseEntity.ok(user);
    }

    /**
     * gets the user, checks if it is valid
     *
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return service.getUser(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found")));
    }


    /**
     * returns every user
     * @return
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(Collections.unmodifiableList(service.getAllUsers()));
    }

    /**
     * gets the current user
     * currently set to get the user with the id 1234L
     *
     * @return
     */
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        return service.getUser(1234L)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * returns the friends of a given user
     * currently set to return the friends of the user with the id "1234L"
     * @return
     */
    @GetMapping("/me/friends")
    public ResponseEntity<Set<User>> getCurrentUserFriends() {
        Set<Long> friendsId = service.getUserFriends(1234L);
        Set<User> friends = new HashSet<>();
        for (Long id : friendsId) {
            service.getUser(id).ifPresent(friends::add);
        }
        return ResponseEntity.ok(Collections.unmodifiableSet(friends));
    }

    /**
     * returns a sorted list for leaderboard
     */
    @GetMapping("/me/friends/leaderboard")
    public ResponseEntity<List<User>> getFriendLeaderBoard(){
        return ResponseEntity.ok(Collections.unmodifiableList(service.friendsLeaderBoard(1234L)));
    }

}

