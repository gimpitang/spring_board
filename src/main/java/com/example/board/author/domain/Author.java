package com.example.board.author.domain;

import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.common.domain.BaseTimeEntity;
import com.example.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Author extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30, nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    //      Enum 은 기본적으로 숫자값으로 db에 들어가기때문에, 별도로 String지정이 필요하다.
    @Enumerated(EnumType.STRING)
    private Role role;
    //      @OneToMany의 기본값이 fetch lazy라 별도의 설정은 없음.
    //      mappedBy의 ManyToOne쪽에 변수명을 문자열로 지정
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    //      빌더패턴에서 변수를 초기화(디폴트값) 할 때 Builder.Default 어노테이션 사용
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    public  void updateProfile(AuthorUpdateReq dto){
        this.name = dto.getName();
        this.password = dto.getPassword();
    }

    public Author(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

    }
    public AuthorListRes listDtoFormEntity(){
        return AuthorListRes.builder().id(this.id).name(this.name).email(this.email).build();
    }
    public AuthorDetailRes detailFromEntity(){
        return AuthorDetailRes.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .postCount(this.posts.size())
                .createdTime(this.getCreatedTime())
                .role(this.role)
                .build();
    }

}
