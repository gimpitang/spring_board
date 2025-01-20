package com.example.board.author.domain;

import com.example.board.author.dtos.AuthorListRes;
import com.example.board.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Author extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30,unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    //      Enum 은 기본적으로 숫자값으로 db에 들어가기때문에, 별도로 String지정이 필요하다.
    @Enumerated(EnumType.STRING)
    private Role role;

    public Author(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

    }
    public AuthorListRes listDtoFormEntity(){
        return AuthorListRes.builder().id(this.id).name(this.name).email(this.email).build();
    }

}
