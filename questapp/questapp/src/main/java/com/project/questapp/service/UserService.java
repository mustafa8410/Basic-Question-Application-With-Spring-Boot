package com.project.questapp.service;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Like;
import com.project.questapp.entities.User;
import com.project.questapp.repository.CommentRepository;
import com.project.questapp.repository.LikeRepository;
import com.project.questapp.repository.PostRepository;
import com.project.questapp.repository.UserRepository;
import com.project.questapp.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//all the "somewhat complex" codes in the UserController that you placed there shouldn't be there,
//the Controller's job is to receive and respond to the requests.
//service will be used to ensure that.
@Service
public class UserService {
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public UserService(UserRepository userRepository, LikeRepository likeRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
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

    public UserResponse getUserResponseById(long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        return new UserResponse(user);
    }

    public User updateOneUser(long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            foundUser.setPassword(newUser.getPassword());
            foundUser.setAvatar(newUser.getAvatar());
            return foundUser;
        }
        else{
            return null;
        }
    }


    public void deleteOneUser(long userId) {
        userRepository.deleteById(userId);
    }

    public User getOneUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<Object> getUserActivity(long userId) {
        List<Long> postIds = postRepository.findTopByUserId(userId);
        if(postIds.isEmpty()){
            return null;
        }
        List<Object> comments = commentRepository.findUserCommentsByPostId(postIds);
        List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
        List<Object> result = new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);
        return result;
    }
}
