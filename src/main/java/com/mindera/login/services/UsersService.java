package com.mindera.login.services;

import com.mindera.login.models.api.LoginRequest;
import com.mindera.login.models.api.LoginResponse;
import com.mindera.login.models.api.UserResponse;
import com.mindera.login.models.database.Session;
import com.mindera.login.models.database.User;
import com.mindera.login.repositories.SessionRepository;
import com.mindera.login.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

/**
 * This class defines a number of methods that perform CRUD operations on the database tables through access to the
 * Users and Session repository methods.
 *
 * The controller endpoints (accessed by the frontend) use this class.
 */

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository; //Allows access to CRUD methods for the Users table.

    @Autowired
    private SessionRepository sessionRepository; //Allows access to CRUD methods for the Session table.


    /**
     * Retrieves a list of all Users currently in the Users table.
     *
     * @return a List of Users
     */

    public List<UserResponse> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getPassword()))
                .collect(toList());
    }

    /**
     * Retrieves a single User from the Users table.
     *
     * @param userId the supplied userId taken from the User table
     * @return the selected User
     */

    public User getUserById(int userId) {
        return usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Creates a new User and adds it to the Users table.
     *
     * @param user the supplied user to be saved to the Users table.
     * @return the newly created User
     */

    public User createNewUser(User user) {
        return usersRepository.save(user);
    }

    /**
     * NEED HELP EXPLAINING
     *
     * Updates an existing User which is reflected in the Users table.
     *
     * Saves only email and password.
     *
     * User id and username are ignored (can't be updated).
     *
     * @param userId
     * @param user
     * @return
     */

    public User updateUser(int userId, User user) {
        User databaseUser = this.getUserById(userId);
        databaseUser.setUsername(user.getUsername());
        databaseUser.setEmail(user.getEmail());
        databaseUser.setPassword(user.getPassword());
        return usersRepository.save(databaseUser);
    }

    /**
     * Removes an existing User
     *
     * @param userId the supplied userId taken from the User table
     * @return the recently deleted User
     */

    public User deleteUser(int userId) {
        User user = this.getUserById(userId);
        usersRepository.delete(user);
        return user;
    }

    /**
     * Login user to back-end server.
     *
     * First checks to see if the inputted username exists in the user table then checks to see if the inputted password
     * also exists in the user table. If both of these checks are satisfied then a session authentication token is
     * generated and saved to the session table.
     *
     * @param request login request containing username and password
     * @return response containing auth token
     */

    public LoginResponse login(LoginRequest request) {
        // search for user by username from input (login request) in database
        Optional<User> optionalUser = usersRepository.findByUsername(request.getUsername());

        // make sure user is found
        User databaseUser = optionalUser.orElseThrow(() ->
                new RuntimeException("Username: " + request.getUsername() + " not found!"));


        if(!isNull(databaseUser.getSession())){
            // If the user has a session I need to make it invalid
            Session session = databaseUser.getSession();
            session.setUser(null);
            session.setExpiryDate(now());
            sessionRepository.save(session);
        }

        // make sure password matches
        if (!databaseUser.getPassword().equals(request.getPassword()))
            throw new RuntimeException("Incorrect Password!");

        // all good -> login user and generate an auth token
        String uniqueId = UUID.randomUUID().toString();
        Session session = new Session(uniqueId, now().plusMinutes(10), databaseUser);

        // Save the session auth token to the Session table
        sessionRepository.save(session);

        return new LoginResponse(session.getSessionAuthToken());
    }

    public User verifyToken(String authToken)
    {
        Optional<Session> optionalSession = sessionRepository.findBySessionAuthTokenAndExpiryDateBefore(authToken, now());

        // boolean isTokenValid = optionalSession.isPresent() && optionalSession.get().getExpiryDate().isAfter(now());

        return optionalSession
                .map(Session::getUser) // if optionalSession value is not null
                .orElse(null); // if optionalSession value is null

    }
}
