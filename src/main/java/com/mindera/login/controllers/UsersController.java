package com.mindera.login.controllers;

import com.mindera.login.models.User;
import com.mindera.login.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = GET)
    public List<User> getAllUsers() {
        return usersService.getAllUsers();
    }

    @RequestMapping(method = GET, path = "/{user-id}")
    public User getUserById(@PathVariable(name = "user-id") int userId) {
        return usersService.getUserById(userId);
    }

    @RequestMapping(method = POST)
    public User createNewUser(@RequestBody User user) {
        return usersService.createNewUser(user);
    }

    @RequestMapping(method = PUT, path = "/{user-id}")
    public User updateUser(@PathVariable(name = "user-id") int userId, @RequestBody User user) {
        return usersService.updateUser(userId, user);
    }

    @RequestMapping(method = DELETE, path = "/{user-id}")
    public User deleteUser(@PathVariable(name = "user-id") int userId) {
        return usersService.deleteUser(userId);
    }

}