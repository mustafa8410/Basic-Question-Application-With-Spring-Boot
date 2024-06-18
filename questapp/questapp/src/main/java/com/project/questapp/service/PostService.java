package com.project.questapp.service;

import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repository.PostRepository;
import com.project.questapp.request.PostCreateRequest;
import com.project.questapp.request.PostUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getAllPosts(Optional<Long> userId) {
        if(userId.isPresent()){
            return postRepository.findByUserId(userId.get());
        }
        else{
            return postRepository.findAll();
        }
    }
    public Post getOnePost( Long postId){
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user = userService.getOneUser(newPostRequest.getUserId());
        if(user == null){
            return null;
        }
        Post toSave = new Post();
        toSave.setId(newPostRequest.getId());
        toSave.setText(newPostRequest.getText());
        toSave.setTitle(newPostRequest.getTitle());
        toSave.setUser(user);
        return postRepository.save(toSave);
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest postUpdateRequest) {
        Optional<Post> toUpdate = postRepository.findById(postId);
        if(toUpdate.isPresent()){
            Post postToUpdate = toUpdate.get();
            postToUpdate.setText(postUpdateRequest.getText());
            postToUpdate.setTitle(postUpdateRequest.getTitle());
            return postRepository.save(postToUpdate);
        }
        else{
            return null;
        }
    }

    public void deleteOnePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}
