package com.mindera.login.services;

import com.mindera.login.models.api.LoginRequest;
import com.mindera.login.models.api.LoginResponse;
import com.mindera.login.models.database.Session;
import com.mindera.login.models.database.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mindera.login.repositories.UsersRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    /**
     * @return
     */
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    /**
     * @param userId
     * @return
     */
    public User getUserById(int userId) {
        return usersRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * @param user
     * @return
     */
    public User createNewUser(User user) {
        return usersRepository.save(user);
    }

    /**
     * Updates user.
     * Saves only email and password.
     * User id and username are ignored (can't be updated).
     *
     * @param userId
     * @param user
     * @return
     */
    public User updateUser(int userId, User user) {
        User databaseUser = this.getUserById(userId);
        databaseUser.setEmail(user.getEmail());
        databaseUser.setPassword(user.getPassword());
        return usersRepository.save(databaseUser);
    }

    /**
     * @param userId
     * @return
     */
    public User deleteUser(int userId) {
        User user = this.getUserById(userId);
        usersRepository.delete(user);
        return user;
    }

    /**
     * Login user to back-end server and generates auth token.
     *
     * @param request login request containing username and password
     * @return response containing auth token
     */
    public LoginResponse login(LoginRequest request) {
        // search for user by username from input (login request) in database
        Optional<User> optionalUser = usersRepository.findByUsername(request.getUsername());

        // make sure user is found
        User databaseUser = optionalUser.orElseThrow(() -> new RuntimeException("User not found by username: " + request.getUsername()));

        // make sure password matches
        if (!databaseUser.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("User password doesn't match");
        }

        // all good -> login user
        Session session = new Session(UUID.randomUUID().toString(), LocalDateTime.now().plusMinutes(10));

        // Save the session in the Session table
        // sessionRepository.save(session);

        return new LoginResponse(session.getSessionAuthToken());
    }
}
