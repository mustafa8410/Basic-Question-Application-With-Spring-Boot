package com.project.questapp.service;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repository.CommentRepository;
import com.project.questapp.request.CommentCreateRequest;
import com.project.questapp.request.CommentUpdateRequest;
import com.project.questapp.response.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<CommentResponse> getAllCommentsWithParam(Optional<Long> postId, Optional<Long> userId) {
        List<Comment> comments;
        if(userId.isPresent() && postId.isPresent()) {
            comments = commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()) {
            comments = commentRepository.findByUserId(userId.get());
        }else if(postId.isPresent()) {
            comments = commentRepository.findByPostId(postId.get());
        }else
            comments = commentRepository.findAll();
        return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
    }

    public Comment getOneComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest commentCreateRequest) {
        User user = userService.getOneUser(commentCreateRequest.getUserId());
        Post post = postService.getOnePost(commentCreateRequest.getPostId());
        if(user == null || post == null)
            return null;
        else{
            Comment newComment = new Comment();
            newComment.setText(commentCreateRequest.getText());
            newComment.setUser(userService.getOneUser(commentCreateRequest.getUserId()));
            newComment.setPost(postService.getOnePost(commentCreateRequest.getPostId()));
            newComment.setCreateDate(new Date());
            return commentRepository.save(newComment);
        }
    }

    public Comment updateOneComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if(commentOptional.isPresent()){
            Comment toUpdate = commentOptional.get();
            toUpdate.setText(commentUpdateRequest.getText());
            return commentRepository.save(toUpdate);
        }
        else return null;
    }

    public void deleteOneComment(Long commentId) {
        Optional<Comment> toDelete = commentRepository.findById(commentId);
        toDelete.ifPresent(commentRepository::delete);
    }
}
