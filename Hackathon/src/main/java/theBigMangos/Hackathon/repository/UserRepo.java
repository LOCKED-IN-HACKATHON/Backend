package theBigMangos.Hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import theBigMangos.Hackathon.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
