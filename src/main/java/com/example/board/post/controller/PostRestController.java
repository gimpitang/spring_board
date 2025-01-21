package com.example.board.post.controller;

import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.post.domain.Post;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.dtos.PostUpdateReq;
import com.example.board.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostUpdate;
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

    @GetMapping("/detail/{id}")
    public PostDetailRes postDetail (@PathVariable Long id) {
        return postService.findById(id);
    }

    @PostMapping("/update/{id}")
    public String postUpdate(@PathVariable Long id, PostUpdateReq dto){
        postService.update(id,dto);
        return "Id is successfully updated";
    }



    @GetMapping("/delete/{id}")
    public String postDelete(@PathVariable Long id) {
        postService.delete(id);
        return "Id is successfully deleted";
    }



}
