package com.example.board.author.controller;

import com.example.board.author.Service.AuthorService;
import com.example.board.author.domain.Author;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/create")
    public String authorCreate(@Valid AuthorSaveReq authorSaveReq) {
        authorService.save(authorSaveReq);
        return "OK";
    }


    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<AuthorListRes> authorListResList = authorService.findAll();
        return new ResponseEntity<>(authorListResList, HttpStatus.OK);
    }
}
