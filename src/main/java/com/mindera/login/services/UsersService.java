package com.mindera.login.services;

import com.mindera.login.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mindera.login.repositories.UsersRepository;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;


    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public User getUserById(int userId) {
        return usersRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

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

    public User deleteUser(int userId) {
        User user = this.getUserById(userId);
        usersRepository.delete(user);
        return user;
    }

}
