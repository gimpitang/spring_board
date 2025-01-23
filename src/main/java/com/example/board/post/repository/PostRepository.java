package com.example.board.post.repository;

import com.example.board.author.domain.Author;
import com.example.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
//    public void findByAuthor (Author author);

    //      paging에서 사용할 메서드
    Page<Post> findAll(Pageable pageable);

    Page<Post>findAllByAppointment(Pageable pageable,String appointment);

    //      jpql을 사용한 일반 join(필터링 걸기 위함 -> N+1문제 그대로 발생함.)
    //      이거 쓰면 뒷쪽에 author에 관한 내용까지 붙어서 조회 할 수는 없음.
    @Query("select p from Post p inner join  p.author")
    List<Post> findAllJoin();

    //      jpql을 사용한 fetch join
    //      순수 쿼리 : select * from post p left join author a on a.id=p.author_id
    //      프로그램 상에서 auhtor_id가 null이 되지 않지만 inner을 쓰면 글쓴이가 익명인경우 게시물이 조회가 되지 않기때문에 개념적으로는
    //      left join이 맞음
    @Query("select p from Post p inner join fetch p.author")
    List<Post> findAllFetchJoin();
}
