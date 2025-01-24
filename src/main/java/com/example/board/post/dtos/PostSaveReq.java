package com.example.board.post.dtos;

import com.example.board.author.domain.Author;
import com.example.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostSaveReq {
    @NotEmpty
    private String title;
    private String contents;
    @NotEmpty
//    private String email; 어센티케이션하면서 뺏음
    private String appointment ;
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")   //방법2
//    private LocalDateTime appointmentTime;   //방법2
    private String appointmentTime;     //방법1 +강사님

    public Post toEntity(Author author, LocalDateTime appointmentTime) {
        return Post.builder()
                .title(this.title)
                .contents(this.contents)
                .author(author)
                .appointment(this.appointment)
//                .appointmentTime(this.appointmentTime)    //방법2
//                .appointmentTime(LocalDateTime.parse(appointmentTime))     //방법1
                .appointmentTime(appointmentTime)       //강사님
                .build();
    }
}
