package com.project.questapp.service;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repository.CommentRepository;
import com.project.questapp.request.CommentCreateRequest;
import com.project.questapp.request.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getAllComments(Optional<Long> postId, Optional<Long> userId) {
        if(postId.isEmpty() && userId.isEmpty())
            return commentRepository.findAll();
        if(userId.isPresent()){
            User user = userService.getOneUser(userId.get());
            if(postId.isPresent()){
                Post post = postService.getOnePost(postId.get());
                return commentRepository.findAllByPostAndUser(post, user);
            }
            else //only userId
                return commentRepository.findAllByUser(user);
        }
        Post post = postService.getOnePost(postId.get());
        return commentRepository.findAllByPost(post);
    }

    public Comment getOnePost(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent())
            return comment.get();
        else return null;
    }

    public Comment createOneComment(CommentCreateRequest commentCreateRequest) {
        User user = userService.getOneUser(commentCreateRequest.getUserId());
        Post post = postService.getOnePost(commentCreateRequest.getPostId());
        if(user == null || post == null)
            return null;
        else{
            Comment newComment = new Comment();
            newComment.setId(commentCreateRequest.getId());
            newComment.setText(commentCreateRequest.getText());
            newComment.setUser(userService.getOneUser(commentCreateRequest.getUserId()));
            newComment.setPost(postService.getOnePost(commentCreateRequest.getPostId()));
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
        if(toDelete.isPresent())
            commentRepository.delete(toDelete.get());
    }
}
