package pl.edu.pbs.ipodloga;

import com.google.cloud.firestore.Firestore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.pbs.ipodloga.Model.FirestoreReader;
import pl.edu.pbs.ipodloga.Model.StudentReader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@SpringBootApplication
public class IpodlogaApplication {

	@Configuration
	public class CorsConfig {

		@Bean
		public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
							.allowedOrigins("http://localhost:3000")
							.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
							.allowCredentials(true);
				}
			};
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(IpodlogaApplication.class, args);
		FirebaseConfig firestoreConfig = new FirebaseConfig();
		Firestore firestore = firestoreConfig.firestore();
		FirestoreReader firestoreReader = new FirestoreReader(firestore);
		StudentReader studentReader = new StudentReader(firestore);

		firestoreReader.wyswietlWszystkieProjekty();
		studentReader.wyswietlWszystkichStudentow();
	}
}
