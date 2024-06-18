package com.project.questapp.repository;

import com.project.questapp.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserIdAndPostId(Long aLong, Long aLong1);
}
