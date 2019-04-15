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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

/**
 * This class defines a number of methods that perform CRUD operations on the database tables through access to the
 * Users and Session repository methods. It also has methods that authenticate a user before they can carry out CRUD
 * operations.
 * <p>
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
     * @return a List of UserResponse instead of a list of User
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
    public UserResponse getUserById(int userId) {
        User user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
    }

    /**
     * Creates a new User and adds it to the Users table.
     *
     * @param user the supplied user to be saved to the Users table.
     * @return the newly created User
     */
    public UserResponse createNewUser(User newUser) {
        User user = usersRepository.save(newUser);
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
    }

    /**
     * Updates an existing User which is reflected in the Users table.
     * <p>
     * First get the correct user by passing the supplied userId into the getUserById method and assign this to a new
     * variable databaseUser. We now have the correct user to update.
     * <p>
     * Next we set the username, email and password details of the old database User and replace them with the passed
     * in details of the supplied User via the get methods.
     * <p>
     * Finally we save the updated databaseUser object back to the Users table.
     * <p>
     * User id is ignored (can't be updated).
     *
     * @param userId the supplied userId taken from the Users table.
     * @param user   the supplied User which contains the updated User details.
     * @return the recently updated User.
     */
    public UserResponse updateUser(int userId, User user) {
        User databaseUser = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        databaseUser.setUsername(user.getUsername());
        databaseUser.setEmail(user.getEmail());
        databaseUser.setPassword(user.getPassword());

        User savedUser = usersRepository.save(databaseUser);
        return new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getPassword());
    }

    /**
     * Removes an existing User from the Users table.
     *
     * @param userId the supplied userId taken from the User table
     */
    public void deleteUser(int userId) {
        usersRepository.deleteById(userId);
    }

    /**
     * Login user to back-end server which will unlock the ability to make CRUD operations on Users table.
     * <p>
     * First we find the inputted User in the Users table by searching the username and providing the username in the
     * input request to the findByUsername method. We assign the result of this to the variable optionalUser which has
     * a type of Optional<User>. This type either returns empty / null / nothing if the inputted username isn't found
     * or the User if the inputted username is found in the Users table.
     * <p>
     * Next we take the result of the optionalUser variable and if empty is returned (username not found in the users
     * table) then we throw an exception else we don't. This is then assigned to the databaseUser variable of type User
     * which is conformation that the inputted username, maps to a valid User stored in the Users table. which is why
     * we can assign this to a variable of type User.
     * <p>
     * Now we need to ensure that if the User has an existing session token, we invalidate it so a new one can be
     * generated with the new login request. This prevents a User having more than one session at a time. If the User's
     * session is not null then we infer that they have an existing one, so we assign the existing User's session to
     * the session variable and set the User of that session to null and set the expiry date to now to make sure the
     * token becomes expired. This invalidated session is then saved to the Session table with no associated User.
     * <p>
     * Next if the password of the User in the database is not equal to the inputted password from the request, an
     * exception is thrown with a message that states the inputted password was incorrect.
     * <p>
     * Once these checks have been satisfied we generate a new random String and assign that to the uniqueId variable
     * and supply that to a new Session along with the expiry date and the User taken from the databaseUser variable.
     * The newly created session is then inserted into the Session table.
     * <p>
     * Finally we return a new LoginResponse supplied with the auth-token of the newly created session.
     *
     * @param request the supplied LoginRequest containing a username and password.
     * @return a LoginResponse containing an auth token.
     */
    public LoginResponse login(LoginRequest request) {
        // search for user by username from input (login request) in database
        Optional<User> optionalUser = usersRepository.findByUsername(request.getUsername());

        // make sure user is found
        User databaseUser = optionalUser.orElseThrow(() ->
                new RuntimeException("Username: " + request.getUsername() + " not found!"));

        if (!isNull(databaseUser.getSession())) {
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

    @Transactional
    public boolean logout(String sessionToken) {
        Session session = sessionRepository
                .findBySessionAuthToken(sessionToken)
                .orElseThrow(() -> new RuntimeException("Session not found by token: " + sessionToken));
        User user = session.getUser();

        user.setSession(null);
        session.setUser(null);

        usersRepository.save(user);

        sessionRepository.delete(session);

        return true;
    }

    /**
     * Verifies that the auth-token in the browsers local storage matches an auth-token in the Session table.
     * <p>
     * First we find a session auth-token with it's expiry date against the supplied auth-token taken from the browser
     * and assign that to an optional variable optionalSession.
     * <p>
     * Then we return NOT SURE YET
     *
     * @param authToken the supplied auth-token taken from the browser local storage.
     * @return NOT SURE YET
     */
    public User verifyToken(String authToken) {
        Optional<Session> optionalSession = sessionRepository.findBySessionAuthTokenAndExpiryDateAfter(authToken, now());

        // boolean isTokenValid = optionalSession.isPresent() && optionalSession.get().getExpiryDate().isAfter(now());

        return optionalSession
                .map(Session::getUser) // if optionalSession value is not null
                .orElse(null); // if optionalSession value is null
    }
}
