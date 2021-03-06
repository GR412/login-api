package com.mindera.login.controllers;

import com.mindera.login.models.api.UserResponse;
import com.mindera.login.models.database.User;
import com.mindera.login.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * This class is an endpoint that is exposed to the frontend. This is achieved via the request mapping URL '/users'.
 * <p>
 * Each request from the frontend has a defined backend URL with both the request mapping '/users' followed by
 * additional parameters such as userID.
 * <p>
 * With this information the frontend calls the correct controller method based on the URL matching.
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    /**
     * Processes an incoming GET request to get all Users from the Users table.
     * <p>
     * Frontend uses http://localhost:8080/users to call this method.
     *
     * @return the result of calling the getAllUsers usersService method.
     */
    @RequestMapping(method = GET)
    public List<UserResponse> getAllUsers() {
        return usersService.getAllUsers();
    }

    /**
     * Processes an incoming GET request to get a single User from the Users table.
     * <p>
     * Frontend uses http://localhost:8080/users/{user-id} to call this method.
     *
     * @return the result of calling the getUserById usersService method.
     */
    @RequestMapping(method = GET, path = "/{user-id}")
    public UserResponse getUserById(@PathVariable(name = "user-id") int userId) {
        return usersService.getUserById(userId);
    }

    /**
     * Processes an incoming POST request to add a new User to the Users table.
     * <p>
     * Frontend uses http://localhost:8080/users to call this method.
     *
     * @return the result of calling the createNewUser usersService method.
     */
    @RequestMapping(method = POST)
    public UserResponse createNewUser(@RequestBody User user) {
        return usersService.createNewUser(user);
    }

    /**
     * Processes an incoming PUT request to update an existing User in the Users table.
     * <p>
     * Frontend uses http://localhost:8080/users/{user-id} to call this method.
     *
     * @return the result of calling the updateUser usersService method.
     */
    @RequestMapping(method = PUT, path = "/{user-id}")
    public UserResponse updateUser(@PathVariable(name = "user-id") int userId, @RequestBody User user) {
        return usersService.updateUser(userId, user);
    }

    /**
     * Processes an incoming DELETE request to delete an existing User from the Users table.
     * <p>
     * Frontend uses http://localhost:8080/users/{users-id} to call this method.
     */
    @RequestMapping(method = DELETE, path = "/{user-id}")
    public void deleteUser(@PathVariable(name = "user-id") int userId) {
        usersService.deleteUser(userId);
    }
}