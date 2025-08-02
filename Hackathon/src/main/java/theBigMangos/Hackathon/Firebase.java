//package theBigMangos.Hackathon;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//@Configuration
//public class Firebase {
//
//
//    @Bean
//    public FirebaseApp initializeFirebase() throws IOException {
//        InputStream serviceAccount = new ClassPathResource("key.json").getInputStream();
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        if (FirebaseApp.getApps().isEmpty()) {
//            FirebaseApp.initializeApp(options);
//            System.out.println(("Firebase application has been initialized"));
//        }
//
//        return FirebaseApp.getInstance();
//    }
//
//}