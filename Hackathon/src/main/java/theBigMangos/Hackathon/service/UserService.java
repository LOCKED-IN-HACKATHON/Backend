package theBigMangos.Hackathon.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import theBigMangos.Hackathon.model.User;
import theBigMangos.Hackathon.repository.UserRepo;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


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

    /**
     * Send a friend request to a given user
     *
     * @param fromId
     * @param toId
     */
    @Transactional
    public void sendRequest(Long fromId, Long toId){
        User sender = getUser(fromId).get();
        User to = getUser(toId).get();
        to.recieveRequest(sender.getId());
    }

    /**
     * Accept Friend Request Logic
     * @param user
     * @param from
     */
    @Transactional
    public void acceptRequest(Long user, Long from){
        User userToAccept = getUser(user).get();
        User fromUser = getUser(from).get();
        userToAccept.acceptRequest(fromUser.getId());
        fromUser.acceptRequest(userToAccept.getId());
    }

    /**
     * Removes friends from both users
     *
     * @param fromId
     * @param toId
     */
    @Transactional
    public void removeFriend(Long fromId, Long toId) {
        User remover = getUser(fromId).get();
        User toRemove =  getUser(toId).get();
        remover.removeFriend(toRemove.getId());
        toRemove.removeFriend(fromId);
    }

    /**
     * get friends list of a user
     *
     * @param userId
     * @return
     */
    public Set<Long> getUserFriends(Long userId) {
        return repo.findById(userId)
                .map(User::getFriends)
                .orElse(Collections.emptySet());
    }
    @Transactional
    public void startStudy(Long userId){
        repo.findById(userId).get().startStudy();
    }

    @Transactional
    public String endStudy(Long userId){
        return repo.findById(userId).get().endStudy();
    }

    /**
     * Get a users friend requests
     *
     * @param userId
     * @return
     */
    public Set<Long> getUserFriendRequests(Long userId) {
        return Collections.unmodifiableSet(repo.findById(userId)
                .map(User::getFriendRequests)
                .orElse(Collections.emptySet()));
    }

    /**
     * returns a sorted list by score
     */
    public List<User> friendsLeaderBoard(Long userId) {
        Set<Long> friendIds = getUserFriends(userId);
        List<User> friends = friendIds.stream()
                .map(this::getUser)
                .flatMap(Optional::stream)
                .collect(Collectors.toCollection(ArrayList::new));

        getUser(userId).ifPresent(friends::add);
        friends.sort(Comparator.comparingInt(User::getScore).reversed());
        return Collections.unmodifiableList(friends);
    }




    /**
     * creates a User with a given name
     */
    public User createUser(String username) {
        User user = new User();
        user.setId(idCounter.incrementAndGet());
        user.setUsername(username);
        return repo.save(user);
    }

    /**
     * gets a user by id
     * @param id
     * @return
     */
    public Optional<User> getUser(Long id) {
        return repo.findById(id);
    }

    /**`
     * gets all users
     * @return
     */
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    /**
     * deletes a user from repo
     * @param id
     */
    public void deleteUser(Long id) {
        repo.deleteById(id);
    }

    /**
     * save a user into the repo
     * @param user
     */
    public User saveUser(User user) {
        return repo.save(user);
    }
}
