package com.example.board.author.Service;

import com.example.board.author.domain.Author;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;


    public void save(AuthorSaveReq authorSaveReq) throws IllegalArgumentException {
        if(authorRepository.findByEmail(authorSaveReq.getEmail()).isPresent()){
            throw new IllegalArgumentException("email already exists");
        }

        // 아래 예외는 dto 레벨에서 어노테이션 Size 사용해서 해결하는 것으로
//        if(authorSaveReq.getPassword().length()<8){
//            throw new IllegalArgumentException("password must be at least 8 characters");
//        }
        authorRepository.save(authorSaveReq.toEntity());


    }

    public List<AuthorListRes> findAll() {
        return authorRepository.findAll().stream().map(a->a.listDtoFormEntity()).collect(Collectors.toList());
    }
}
