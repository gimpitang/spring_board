package com.example.board.author.controller;

import com.example.board.author.Service.AuthorService;
import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.dtos.AuthorUpdateReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/detail/{id}")
    public AuthorDetailRes authorDetail (@PathVariable Long id) {
        return authorService.findById(id);
    }

    @GetMapping("/delete/{id}")
    public String authorDelete(@PathVariable Long id) {
        authorService.delete(id);
        return "Id is successfully deleted";
    }

    @PostMapping("/update/{id}")
    public String authorUpdate(@PathVariable Long id, AuthorUpdateReq dto){
        authorService.update(id,dto);
        return "Id is successfully updated";
    }


}
