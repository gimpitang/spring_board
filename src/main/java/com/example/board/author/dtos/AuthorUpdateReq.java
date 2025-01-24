package com.example.board.author.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

// 업데이트 암호를 바꿀 때에는 author와 마찬가지로 암호화 과정을 거쳐야한다.
public class AuthorUpdateReq {
    private String name;
    private String password;
}
