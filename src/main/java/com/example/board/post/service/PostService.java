package com.example.board.post.service;

import com.example.board.author.domain.Author;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.domain.Post;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.dtos.PostUpdateReq;
import com.example.board.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PostUpdate;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;

    public PostService(AuthorRepository authorRepository, PostRepository postRepository) {
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
    }

    public void save(PostSaveReq dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        dto.getEmail() 자리에 email 들어감 (authentication
        Author author  = authorRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("Author not found"));
//        Post post = postSaveReq.toEntity(author);     //방법 1,2
//        postRepository.save(postSaveReq.toEntity(author));        //방법 1,2
        LocalDateTime appointmentTime = null;
        if (dto.getAppointment().equals("Y")) {
            if (dto.getAppointmentTime().isEmpty() || dto.getAppointmentTime() == null) {
                throw new IllegalArgumentException("시간이 비어져 있습니다.");
            } else {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                appointmentTime = LocalDateTime.parse(dto.getAppointmentTime(), dateTimeFormatter);
                LocalDateTime now = LocalDateTime.now();
                if (appointmentTime.isBefore(now)) {
                    throw new IllegalArgumentException("시간이 과거입니다.");
                }
            }
        }
            postRepository.save(dto.toEntity(author, appointmentTime));

    }

    public List<PostListRes> findAll() {
        return postRepository.findAll().stream().map(p->p.postListRes()).collect(Collectors.toList());

    }
    public Page<PostListRes> findAllPaging(Pageable pageable) {
//        Page<Post> pagePosts = postRepository.findAll(pageable); //레포지토리에 findAllByAppointment만들면서 이거 주석함.(스케줄)
        Page<Post> pagePosts = postRepository.findAllByAppointment(pageable,"N");
        return pagePosts.map(p->p.postListRes());

        //    public List<PostListRes> findAllPaging(Pageable pageable) {
//        return postRepository.findAll().stream().map(p->p.postListRes()).collect(Collectors.toList());

    }


    public PostDetailRes findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        return post.detailFromEntity();
    }

    public void update(Long id, PostUpdateReq postUpdateReq) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        post.updatePost(postUpdateReq);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);

        //      아래처럼 검증과정을 거친 후 삭제할 수도 있다.
//        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
//        postRepository.delete(post);
    }
    public List<PostListRes> listFetchJoin(){

////        일반 JOIN : author를 join해서 post를 조회하긴 하나, author의 데이터는 실제 참조할때 쿼리가 N번발생
//        List<Post> postList= postRepository.findAllJoin(); //       쿼리 1번나감
//        FETCH JOIN : author를 join해서 조회하고, author의 데이터도 join시점에서 가져옴. 쿼리1번발생
        List<Post> postList= postRepository.findAllFetchJoin();

        return postList.stream().map(p->p.postListRes()).collect(Collectors.toList()); // 쿼리 N번 나감
    }
}
