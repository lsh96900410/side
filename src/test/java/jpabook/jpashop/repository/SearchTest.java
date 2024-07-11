package jpabook.jpashop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.keyword.Keyword;
import jpabook.jpashop.domain.keyword.KeywordRepository;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.member.MemberRepository;
import jpabook.jpashop.domain.todo.FeedQuery;
import jpabook.jpashop.domain.todo.QTodo;
import jpabook.jpashop.domain.todo.Todo;
import jpabook.jpashop.domain.todo.TodoRepository;
import jpabook.jpashop.dtos.FeedDto;
import jpabook.jpashop.dtos.FormData;
import jpabook.jpashop.dtos.ServiceDto;
import jpabook.jpashop.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@SpringBootTest
public class SearchTest {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private FeedQuery feedQuery;

    @Autowired
    TodoService todoService;
    @Test
    public void getRsult(){
//        List<Todo> fetch = jpaQueryFactory.selectFrom(todo).where(todo.title.contains("테스트")).fetch();
//        QTodo todo= QTodo.todo;

        Pageable pageable = PageRequest.of(0,10);
        long startTest2 = System.currentTimeMillis();
        feedQuery.feedFindAll(pageable);
        long endTest2 = System.currentTimeMillis();
        System.out.println(" ============================= 변경 쿼리에요 속도는 : " +(endTest2-startTest2) +" ms");

//        feedQuery.test1(pageable);
        Pageable pageable1 = PageRequest.of(0,10,Sort.by(Sort.Direction.DESC,"viewCount"));
        //todoService.findTodos();
        long startTest1 = System.currentTimeMillis();
        Page<Todo> all = todoRepository.findAll(pageable1);
        long endTest1 = System.currentTimeMillis();
        System.out.println(" ============================= 기존 쿼리에요 속도는 : " +(endTest1-startTest1) +" ms");


        Page<ServiceDto.AllTodosDto> pagingTodo = all.map(feedDto -> new ServiceDto.AllTodosDto(feedDto));
        List<ServiceDto.TopTodo> topTodos =
                all.stream().limit(5).map(topTodo -> new ServiceDto.TopTodo(topTodo)).collect(Collectors.toList());
        List<Member> topMembers = memberRepository.findTop6ByOrderByTotalViewCountDesc();

    }

    @Test
    void indexTest(){
        Pageable pageable1 = PageRequest.of(0,10,Sort.by(Sort.Direction.DESC,"viewCount"));
        System.out.println(" ============================= 쿼리시작 ===============");
        long startTest1 = System.currentTimeMillis();
        Page<Todo> all = todoRepository.findAll(pageable1);
        long endTest1 = System.currentTimeMillis();
        System.out.println(" ============================= 기존 쿼리에요 속도는 : " +(endTest1-startTest1) +" ms");
    }

//    @Test
//    void tests(){
//        Pageable pageable = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"viewCount"));
//        List<FeedDto> feedDtos = feedQuery.feedFindAll(pageable);
//        System.out.println("우우우웅" + feedDtos.get(0));
//    }




    @Test
    void dataInsert(){
        UUID uuid=UUID.randomUUID();
        String passwords = ("qwe"+uuid);
        if(passwords.length() > 8){
            passwords=passwords.substring(0,8);
        }

        Optional<Keyword> byId = keywordRepository.findById(1L);


        List<Member> ms = new ArrayList<>();
        List<Todo> ts = new ArrayList<>();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        LongStream.rangeClosed(1,100*100).forEach( i ->{
            FormData.Join testJoin = new FormData.Join("회원"+uuid,"pass"+uuid,"이메일"+uuid,"이름"+uuid,"백엔드","고졸");
            Member member=new Member(testJoin,bCryptPasswordEncoder);
            ms.add(member);

            Todo todo = new Todo("",byId.get(),member);
            ts.add(todo);

            if(i==10000) {
                memberRepository.saveAll(ms);
                todoRepository.saveAll(ts);
            }
        });

        stopWatch.stop();
    }


}
