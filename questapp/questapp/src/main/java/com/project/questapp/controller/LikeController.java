package com.project.questapp.controller;
import java.util.List;
import java.util.Optional;

import com.project.questapp.request.LikeCreateRequest;
import com.project.questapp.service.LikeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.questapp.entities.Like;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public Like createOneLike(@RequestBody LikeCreateRequest request) {
        return likeService.createOneLike(request);
    }

    @GetMapping("/{likeId}")
    public Like getOneLike(@PathVariable Long likeId) {
        return likeService.getOneLikeById(likeId);
    }

    @DeleteMapping("/{likeId}")
    public void deleteOneLike(@PathVariable Long likeId) {
        likeService.deleteOneLikeById(likeId);
    }
}
