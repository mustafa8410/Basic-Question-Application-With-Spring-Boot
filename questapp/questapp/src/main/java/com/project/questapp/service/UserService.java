package com.project.questapp.service;

import com.project.questapp.entities.User;
import com.project.questapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//all the "somewhat complex" codes in the UserController that you placed there shouldn't be there,
//the Controller's job is to receive and respond to the requests.
//service will be used to ensure that.
@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User saveOneUser(User newUser) {
        return userRepository.save(newUser);
    }


    public User getOneUser(long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            foundUser.setPassword(newUser.getPassword());
            return foundUser;
        }
        else{
            return null;
        }
    }


    public void deleteOneUser(long userId) {
        userRepository.deleteById(userId);
    }
}
