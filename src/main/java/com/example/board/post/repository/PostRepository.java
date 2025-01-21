package com.example.board.post.repository;

import com.example.board.author.domain.Author;
import com.example.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
//    public void findByAuthor (Author author);

    //      paging에서 사용할 메서드
    Page<Post> findAll(Pageable pageable);
}
