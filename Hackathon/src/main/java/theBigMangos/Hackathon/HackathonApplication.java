package theBigMangos.Hackathon;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import theBigMangos.Hackathon.model.User;
import theBigMangos.Hackathon.service.UserService;

import java.util.*;

@SpringBootApplication
public class HackathonApplication {
//jkhdsjhdbjhdjbds
	public static void main(String[] args) {
		SpringApplication.run(HackathonApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			List<String> usernames = List.of("Alex", "Taylor", "Jordan", "Morgan", "Casey", "Avery", "Riley", "Quinn", "Jamie", "Skyler",
					"James", "John", "Michael", "David", "Robert", "William", "Joseph", "Daniel", "Matthew", "Andrew",
					"Emma", "Olivia", "Ava", "Sophia", "Isabella", "Mia", "Charlotte", "Amelia", "Harper", "Ella");
			List<User> users = new ArrayList<>();

			for(int i = 0; i < usernames.size(); i++) {
				User user = new User();
				user.setUsername(usernames.get(i));
				user.setId((long) i);
				users.add(user);
			}


			User user = new User();
			user.setUsername("Ryan is cool");
			user.setId(1234L);
			for(User u : users) {
				user.addFriend(u.getId());
				userService.saveUser(u);

			}
			userService.saveUser(user);
		};
	}
}

