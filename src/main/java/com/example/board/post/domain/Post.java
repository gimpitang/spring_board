package com.example.board.post.domain;

import com.example.board.author.domain.Author;
import com.example.board.common.domain.BaseTimeEntity;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostUpdateReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 3000)
    private String contents;
    private String appointment;
    private LocalDateTime appointmentTime;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
    public void setMember(Author author) {
        this.author = author;
    }

    public PostListRes postListRes(){
        return PostListRes.builder().id(this.id).title(this.title).authorEmail(this.author.getEmail()).build();
    }
    public PostDetailRes detailFromEntity(){
        return PostDetailRes.builder()
                .id(this.id)
                .title(this.title)
                .contents(this.contents)
                .authorEmail(this.author.getEmail())
                .createdTime(this.getCreatedTime())
                .updatedTime(this.getUpdatedTime())
                .build();
    }

    public void updatePost(PostUpdateReq dto){
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }
}
