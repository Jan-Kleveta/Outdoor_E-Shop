package cz.cvut.fel.sit.security;

import cz.cvut.fel.sit.entity.User;
import cz.cvut.fel.sit.enums.Role;
import cz.cvut.fel.sit.enums.Status;
import cz.cvut.fel.sit.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminAccountInitializer implements ApplicationRunner {

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminAccountInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(Role.ADMIN);
            admin.setStatus(Status.REGISTERED);
            userRepository.save(admin);
            log.info("Admin account created with email: {}", adminEmail);
        }
    }
}
