package theBigMangos.Hackathon;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


/**
 * User class contains the fields:
 *  String username
 *  set of friends
 *  int score
 *  int currentStreak
 *  int bestStreak
 *
 * These are to be used as the base of the project for the user
 */

@Entity
@Table(name = "users")
public class User {


    @Id
    private Long id;
    private String username;
    private Set<Long> friends = new HashSet<>();
    private int score;
    private int highScore;
    private int currentStreak;
    private int bestStreak;
    private LocalDate lastStudyDate;
    private LocalDateTime studyTimeStart;
    private LocalDateTime studyTimeEnded;

    /**
     * constructor for new user
     * set everything to 0
     */
    public User() {
        currentStreak = 0;
        bestStreak = 0;
        score = 0;
        highScore = 0;
    }

    public void addFriend(Long friendId) {friends.add(friendId);}
    public void removeFriend(Long friendId) {friends.remove(friendId);}
    public Set<Long>  getFriends() {return Collections.unmodifiableSet(friends);}
    public Set<Long> seeFriends(){return Collections.unmodifiableSet(friends);}
    public void setId(Long id) {this.id = id;}
    public Long getId() {return id;}
    public void setUsername(String username){this.username = username;}
    public String getUsername() {return username;}
    public int getCurrentStreak(){return currentStreak;}
    public int getBestStreak(){return bestStreak;}

    public void startStudy(){studyTimeStart = LocalDateTime.now();}
    public void endStudy(){studyTimeEnded = LocalDateTime.now();}

    /**
     * checks validity of the streak
     * update if streak is correct
     */
    public boolean isStreakContinuing() {return lastStudyDate != null && lastStudyDate.equals(LocalDate.now().minusDays(1));}
    public void updateStreak(){
        if(isStreakContinuing()) currentStreak++;
        else currentStreak = 0;
        if(currentStreak > bestStreak) bestStreak = currentStreak;
        lastStudyDate =  LocalDate.now();
    }


    /**
     * update the score
     * 10 points per day
     * 2 points per half hour
     * 10 points once 3 hours is hit
     */
    public void updateScore(){
        if(lastStudyDate.equals(LocalDate.now())){
            int halfHours = pointCalc();
            score += 10; // daily score
            score += halfHours * 2; // 2 points per 30 mins
            if(halfHours >= 6) score += 10; // bonus 10 points once hit 3 hours
        }
        if(score > highScore) highScore = score;
    }

    /**
     * helper method to calculate points
     */
    private int pointCalc(){
        Objects.requireNonNull(studyTimeStart, "Study start time is missing");
        Objects.requireNonNull(studyTimeEnded, "Study end time is missing");
        Duration duration = Duration.between(studyTimeStart, studyTimeEnded);
        return (int) duration.toMinutes() / 30;
    }

    public int getScore(){return score;}
    public int getHighScore(){return highScore;}

}

