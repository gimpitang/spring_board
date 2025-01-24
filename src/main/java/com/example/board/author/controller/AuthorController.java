package com.example.board.author.controller;

import com.example.board.author.Service.AuthorService;
import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.author.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorService authorService, AuthorRepository authorRepository) {
        this.authorService = authorService;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/create")
    public String authorCreate() {
        return "author/author_create";
    }

    @PostMapping("/create")
    public String authorCreate(@Valid AuthorSaveReq authorSaveReq) {
        authorService.save(authorSaveReq);
        return "redirect:/";
    }


    @GetMapping("/list")
    public String authorList(Model model) {
        List<AuthorListRes> authorListResList = authorService.findAll();
        model.addAttribute("authorList", authorListResList);
        return "/author/author_list";
    }

    @GetMapping("/detail/{id}")
    public String authorDetail (@PathVariable Long id, Model model) {
        //      "author"라는 변수에 데이터 세팅해서 화면 리턴
        model.addAttribute("author",authorService.findById(id));
        return "/author/author_detail";
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

    @GetMapping("/login")
    public String authorLoginScreen() {
        return "/author/author_login";
    }


}
