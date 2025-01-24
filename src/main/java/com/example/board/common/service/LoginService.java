package com.example.board.common.service;

import com.example.board.author.domain.Author;
import com.example.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//      1. doLogin 호출 시
//      2. spring 에서 구현 한 doLogin 메서드 내에서 loadUserByUsername 을 실행
//      3. 해당 메서드에서 DB 에서 조회한 user 객체를 만들어서 return 해줘야 한다!
//      4. 스프링에서 DB의 user 객체와 사용자가 입력한 email/password 를 비교
//      5. 검증이 완료 되면, DB의 유저 객체를 Authentication 객체에 저장(중요)
@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private AuthorRepository authorRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author =authorRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username));
        List<GrantedAuthority> authorities = new ArrayList<>();

        //      SimpleGrantedAuthority와 GrantedAuthority는 상속관계
//        authorities.add(new SimpleGrantedAuthority(author.getRole().toString())); // 이렇게 해도 되는데 밑에가 룰임
        authorities.add(new SimpleGrantedAuthority("ROLE_"+author.getRole()));
        return new User(author.getEmail(),author.getPassword(),authorities); //user객체는 3가지 요소로 구성: 1.username(email), 2.password 3. role
    }
}
