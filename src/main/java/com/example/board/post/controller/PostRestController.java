package com.example.board.post.controller;

import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.post.domain.Post;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.dtos.PostUpdateReq;
import com.example.board.post.service.PostService;
import net.bytebuddy.TypeCache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostUpdate;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/post")

public class PostRestController {
    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/create")
    public String postCreate(){
        return "post/post_create";
    }

    @PostMapping("/create")
    public String postCreate(@Valid PostSaveReq postSaveReq) {
        postService.save(postSaveReq);
        return "redirect:/post/list";
    }

    @GetMapping("/list")
    private String list(Model model){
        model.addAttribute("postList", postService.findAll());
        return "/post/post_list";
    }

    @GetMapping("/list/paging")
//    @ResponseBody
    //      페이징 처리를 위한 데이터 형식 : localhost:8080/post/list/paging?size=10&page=0&sort=createdTime,desc
//    private List<PostListRes> postListPaging(@RequestParam() "size" = , @RequestParam "size = 10", @RequestParam "size = 10" ){
    public String postListPaging(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model){
        System.out.println(pageable);
//        return postService.findAllPaging();
//            return postService.findAllPaging(pageable);
        model.addAttribute("postList",postService.findAllPaging(pageable) );
        return "/post/post_list";
    }

    @GetMapping("/detail/{id}")
    public String postDetail (@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        return "/post/post_detail";
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
