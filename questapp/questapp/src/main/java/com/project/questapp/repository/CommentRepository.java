package com.project.questapp.repository;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPostAndUser(Post post, User user);
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByUser(User user);
}
