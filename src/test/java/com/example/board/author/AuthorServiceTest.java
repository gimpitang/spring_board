package com.example.board.author;

import com.example.board.author.Service.AuthorService;
import com.example.board.author.domain.Author;
import com.example.board.author.domain.Role;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void authorServiceTest() {
        AuthorSaveReq authorSaveReq = new AuthorSaveReq(
                "bongildong", "dudwodpdy123@gmail.com", "2314233223", Role.USER
        );
        authorService.save(authorSaveReq);

        Author author = authorRepository.findByEmail("dudwodpdy123@gmail.com").orElse(null);

        Assertions.assertEquals("dudwodpdy123@gmail.com", author.getEmail());

    }

    @Test
    public void findAllTest() {
        int beforeSize = authorRepository.findAll().size();
        authorRepository.save(Author.builder().email("aaaaaa1@naver.com").name("asdf").password("1234").build());
        authorRepository.save(Author.builder().email("aaaaaa2@naver.com").name("asdf").password("1234").build());
        authorRepository.save(Author.builder().email("aaaaaa3@naver.com").name("asdf").password("1234").build());
        int afterSize = authorService.findAll().size();

        Assertions.assertEquals(beforeSize + 3, afterSize);

    }

}
