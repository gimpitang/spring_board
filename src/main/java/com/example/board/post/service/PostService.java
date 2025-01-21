package com.example.board.post.service;

import com.example.board.author.domain.Author;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.domain.Post;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.dtos.PostUpdateReq;
import com.example.board.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PostUpdate;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;

    public PostService(AuthorRepository authorRepository, PostRepository postRepository) {
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
    }

    public void save(PostSaveReq postSaveReq) {
        Author author  = authorRepository.findByEmail(postSaveReq.getEmail()).orElseThrow(()-> new EntityNotFoundException("Author not found"));
        Post post = postSaveReq.toEntity(author);
        postRepository.save(postSaveReq.toEntity(author));

    }

    public List<PostListRes> findAll() {
        return postRepository.findAll().stream().map(p->p.postListRes()).collect(Collectors.toList());

    }


    public PostDetailRes findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        return post.detailFromEntity();
    }

    public void update(Long id, PostUpdateReq postUpdateReq) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        post.updatePost(postUpdateReq);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
