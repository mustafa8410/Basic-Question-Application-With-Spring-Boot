package com.project.questapp.repository;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPostAndUser(Post post, User user);
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByUser(User user);

    @Query(value = "select 'commented on', c.post_id, u.avatar, u.user_name from "
            + "comment c left join user u on u.id = c.user_id "
            + "where c.post_id in :postIds limit 5", nativeQuery = true)
    List<Object> findUserCommentsByPostId(@Param("postIds") List<Long> postIds);
}
