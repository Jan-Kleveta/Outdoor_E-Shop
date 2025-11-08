package cz.cvut.fel.sit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.out.println("DB_USER from env: " + System.getenv("DB_USER"));
        SpringApplication.run(App.class, args);
    }
}

