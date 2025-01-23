package com.example.board.author.Service;

import com.example.board.author.domain.Author;
import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorService {
    @Autowired
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;

    public AuthorService(AuthorRepository authorRepository, PostRepository postRepository) {
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
    }


    public void save(AuthorSaveReq authorSaveReq) throws IllegalArgumentException {
        if(authorRepository.findByEmail(authorSaveReq.getEmail()).isPresent()){
            throw new IllegalArgumentException("email already exists");
        }
        if(authorSaveReq.getPassword().length()<8){
            throw new IllegalArgumentException("비번 너무 짧아요");
        }

        // 아래 예외는 dto 레벨에서 어노테이션 Size 사용해서 해결하는 것으로
//        if(authorSaveReq.getPassword().length()<8){
//            throw new IllegalArgumentException("password must be at least 8 characters");
//        }

//        //      cascade를 활용하지 않고, 별도로 past데이터 만드는 경우
        Author author = authorRepository.save(authorSaveReq.toEntity());
//        postRepository.save(Post.builder().title("방가방가").contents("하이요").author(author).build());


        //      cascade를 활용해서,post 테이터를 함께 만드는 경우
//        Author author = Author.builder()
//                .name(authorSaveReq.getName())
//                .email(authorSaveReq.getEmail())
//                .password(authorSaveReq.getPassword())
//                .role(authorSaveReq.getRole())
//                .build();
//        //      post를 생성하는 시점에 author 가 아직 DB에 저장되지 않은 것으로 보여지나, jpa가 author와 post를
//        //      save하는 시점에 선후관계를 맞춰줌
//        author.getPosts().add(Post.builder().title("방가방가!!").contents("하이요!!").author(author).build());
//        author.getPosts().add(Post.builder().title("방가방가!!!").contents("하이요!!!").author(author).build());
//        authorRepository.save(author);

    }

    public List<AuthorListRes> findAll() {
        return authorRepository.findAll().stream().map(a->a.listDtoFormEntity()).collect(Collectors.toList());
    }

    public void delete(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("지울게 없다!"));
        authorRepository.delete(author);
    }

    public void update(Long id, AuthorUpdateReq dto) {
        Author author = authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("지울게 없다!"));
        author.updateProfile(dto);
        //      기존 객체에 변경이 발생할 경우, 별도의 save가 없어도 jpa가 엔티티의 변경을 자동인지하고 변경사항을 DB에 반영
        //      이를 dirtyChecking 이라고 부르고, 반드시 transactional 어노테이션이 있을 경우 동작함.
//        authorRepository.save(author);
    }

    public AuthorDetailRes findById(Long id) {
        Author author =authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("업서용"));
        return author.detailFromEntity();
    }
}
