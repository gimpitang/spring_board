package com.example.board.post.controller;

import com.example.board.post.domain.Post;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")

public class PostRestController {
    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public String postCreate(@Valid PostSaveReq postSaveReq) {
        postService.save(postSaveReq);
        return "OK";
    }

    @GetMapping("/list")
    private List<PostListRes> list(){
        return postService.findAll();
    }
}
