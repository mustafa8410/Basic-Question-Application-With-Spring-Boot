package com.project.questapp.service;

import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repository.PostRepository;
import com.project.questapp.request.PostCreateRequest;
import com.project.questapp.request.PostUpdateRequest;
import com.project.questapp.response.LikeResponse;
import com.project.questapp.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private LikeService likeService;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> postList;
        if(userId.isPresent()){
            postList = postRepository.findByUserId(userId.get());
        }
        else{
            postList = postRepository.findAll();
        }
        return postList.stream().map(p -> {
            List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.empty(), Optional.of(p.getId()));
            return new PostResponse(p, likes);
        }).collect(Collectors.toList());
    }
    public Post getOnePost( Long postId){
        return postRepository.findById(postId).orElse(null);
    }

    public PostResponse getOnePostWithLikes(Long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isEmpty()) return null;
        Post post = optionalPost.get();
        List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.empty(), Optional.of(postId));
        return new PostResponse(post, likes);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user = userService.getOneUser(newPostRequest.getUserId());
        if(user == null){
            return null;
        }
        Post toSave = new Post();
        toSave.setText(newPostRequest.getText());
        toSave.setTitle(newPostRequest.getTitle());
        toSave.setUser(user);
        toSave.setCreateDate(new Date());
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
