package cz.cvut.fel.sit.service;

import cz.cvut.fel.sit.entity.User;
import cz.cvut.fel.sit.enums.Role;
import cz.cvut.fel.sit.enums.Status;
import cz.cvut.fel.sit.dto.request.LoginRequest;
import cz.cvut.fel.sit.repository.UserRepository;
import cz.cvut.fel.sit.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
/**
 * Service responsible for handling user-related operations such as
 * registration, authentication, and account management.
 *
 */
@Slf4j
@Service
public class UserService {

    /**
     * Repository for working with {@code User} entities.
     * Provides access to database operations such as saving and retrieving users.
     */
    private final UserRepository userRepository;

    /**
     * Component for encrypting passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Service for generating and validating JWT (JSON Web Tokens),
     */
    private final JwtService jwtService;

    /**
     * Constructor for initializing UserService.
     *
     * @param userRepository Repository for user data.
     * @param passwordEncoder Encoder for passwords.
     * @param jwtService Service for generating JWT tokens.
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId ID of the user.
     * @return User found by the given ID.
     * @throws NoSuchElementException if the user does not exist.
     */
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        Objects.requireNonNull(userId, "User ID must not be null.");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " does not exist."));

        log.info("Fetched user: {}", user);
        return user;
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email Email address of the user.
     * @return User found by the given email.
     * @throws NoSuchElementException if the user does not exist.
     */
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        Objects.requireNonNull(email, "Email must not be null.");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User with email " + email + " does not exist."));

        log.info("Fetched user by email: {}", user);
        return user;
    }

    /**
     * Creates a new user.
     *
     * @param user User object containing user information.
     * @param passwordCheck Password confirmation for validation.
     * @return Created or reactivated user.
     * @throws IllegalArgumentException if passwords do not match or user already exists.
     */
    @Transactional
    public User createUser(User user, String passwordCheck) {
        if (!user.getPassword().equals(passwordCheck)){
            throw new IllegalArgumentException("Passwords do not match");
        }
        Objects.requireNonNull(user, "User must not be null.");
        Objects.requireNonNull(user.getEmail(), "Email must not be null.");
        Objects.requireNonNull(user.getPassword(), "Password must not be null.");
        Optional<User> existingUserOpt = userRepository.findByEmail(user.getEmail());

        if (existingUserOpt.isEmpty()) {
            log.info("Adding new user: {}", user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setStatus(Status.REGISTERED);
            user.setRole(Role.CUSTOMER);
            return userRepository.save(user);
        }

        User existingUser = existingUserOpt.get();

        if (existingUser.getStatus() == Status.DELETED) {
            log.info("Reactivating deleted user with email: {}", existingUser.getEmail());
            existingUser.setStatus(Status.REGISTERED);
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            return existingUser;
        }

        if (existingUser.getStatus() == Status.BLOCKED) {
            throw new IllegalArgumentException("User is blocked.");
        }

        throw new IllegalArgumentException("User already exists.");
    }

    /**
     * Logs in a user.
     *
     * @param loginRequest Object containing email and password.
     * @return JWT token upon successful login.
     * @throws IllegalArgumentException if credentials are incorrect.
     */
    @Transactional(readOnly = true)
    public String loginUser(LoginRequest loginRequest) {
        Objects.requireNonNull(loginRequest, "Login request must not be null.");
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NoSuchElementException("Wrong email or password."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong email or password.");
        }

        log.info("User {} logged in successfully.", loginRequest.getEmail());
        return jwtService.generateToken(user.getEmail(), String.valueOf(user.getRole()));
    }

    /**
     * Soft deletes a user by their ID.
     *
     * @param userId ID of the user to be deleted.
     * @throws NoSuchElementException if the user does not exist.
     */
    @Transactional
    public void deleteUserById(Long userId) {
        Objects.requireNonNull(userId, "User ID must not be null.");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " does not exist."));

        log.info("Deleting user: {}", user);
        user.setStatus(Status.DELETED);
    }
}
