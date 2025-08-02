package theBigMangos.Hackathon.controller;

import com.google.firebase.messaging.*;
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
        System.out.println(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/friend-request")
    public ResponseEntity<Boolean> sendFriendRequest(@RequestParam Long fromId, @RequestParam Long toId) {
        service.sendRequest(fromId, toId);
        System.out.println("Friend request from " + fromId + " to " + toId);
        return ResponseEntity.ok(true);
    }

    /**
     * Start study
     * @param id
     */
    @PostMapping("/start-Study")
    public ResponseEntity<Integer> startStudy(@RequestParam Long id) {
        service.startStudy(id);
        System.out.println("Study started for student: " + id);
        return ResponseEntity.ok(service.getUser(id).get().getScore());
    }

    /**
     * end study session
     * @param id
     */
    @PostMapping("/end-Study")
    public ResponseEntity<Integer> endStudy(@RequestParam Long id) {
        service.endStudy(id);
        System.out.println("Study ended for student: " + id);
        return ResponseEntity.ok(service.getUser(id).get().getScore());
    }

    @PostMapping("/get-Points")
    public ResponseEntity<Integer> getPoints(@RequestParam Long id){
        return ResponseEntity.ok(service.getUser(id).get().getScore());
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

    @CrossOrigin
    @GetMapping("/addnotif/{notif}")
    public ResponseEntity<Boolean> addNotif(@PathVariable String notif) {
        notifs.add(notif);
        System.out.println("Added notif " + notif);
        return ResponseEntity.ok(true);
    }

    @CrossOrigin
    @GetMapping("/sendnotifs")
    public ResponseEntity<Boolean> sendAllNotifs() {
        String tempLink = "https://04ef82f0639f.ngrok-free.app/";
        notifs.forEach(n -> {
            Notification notification = Notification.builder()
                    .setTitle("title")
                    .setBody("body")
                    .build();

            Message message = Message.builder()
                    .setNotification(notification)
                    .setToken(n)
                    .setWebpushConfig(WebpushConfig.builder()
                            .setFcmOptions(WebpushFcmOptions.builder()
                                    .setLink(tempLink)
                                    .build())
                            .build())
                    .build();
            String response = null;
            try {
                response = FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Successfully sent message: " + response);
        });



        return ResponseEntity.ok(true);
    }

    private final Set<String> notifs = new HashSet<>();

}

