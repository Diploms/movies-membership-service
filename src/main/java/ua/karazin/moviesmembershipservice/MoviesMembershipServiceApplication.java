package ua.karazin.moviesmembershipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoviesMembershipServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MoviesMembershipServiceApplication.class, args);
  }

}
