package jpabook.jpashop.service;

import static jpabook.jpashop.constant.NumberName.*;

import jpabook.jpashop.constant.ExceptionType;
import jpabook.jpashop.constant.NumberName;
import jpabook.jpashop.domain.keyword.Keyword;
import jpabook.jpashop.domain.keyword.KeywordRepository;
import jpabook.jpashop.domain.like.LikeRepository;
import jpabook.jpashop.domain.like.Likes;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.member.MemberRepository;
import jpabook.jpashop.domain.todo.FeedQuery;
import jpabook.jpashop.domain.todo.Todo;
import jpabook.jpashop.domain.todo.TodoRepository;
import jpabook.jpashop.dtos.*;
import jpabook.jpashop.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final KeywordRepository keywordRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final RedisTemplate<String,Object> redisTemplate;
    private final CacheManager cacheManager;
    private final FeedQuery feedQuery;

    public List<ServiceDto.AllTodosDto> searchByTitle(RequestDto.Search search){

        List<Todo> todos = todoRepository.searchTodoByTitle(search);

        List<ServiceDto.AllTodosDto> resultSearchTodos =
                todos.stream().map(todo -> new ServiceDto.AllTodosDto(todo)).collect(Collectors.toList());

        return resultSearchTodos;
    }


    /* Todo 조회  */
    @Transactional
    public ResponseDto.TodoDetail todoDetails(Long todoId,Long loginMemberId){

        Todo findTodo = todoRepository.findById(todoId)
                .orElseThrow(()-> new CustomException(ExceptionType.NOT_EXIST_TODO));

        List<Todo> top5Todos = todoRepository.findTop5ByOrderByViewCountDesc();

        List<ServiceDto.TopTodo> topTodos =
                top5Todos.stream().map(topTodo ->
                        new ServiceDto.TopTodo(topTodo)).collect(Collectors.toList());

        ResponseDto.TodoDetail todoDetail = new ResponseDto.TodoDetail(findTodo,topTodos);

        /* 좋아요 기록 체크 */
        Optional<Likes> checkExistLike = likeRepository.findByMemberIdAndTodoId(loginMemberId, todoId);

        checkExistLike.ifPresent(alreadyExistLike -> todoDetail.alreadyLikeState());

        /* 자기 자신 투두는 ViewCount 증가 X */
        if(findTodo.getMember().getId()!=loginMemberId){
            findTodo.upViewCount();
            findTodo.getMember().upViewCount();
        }

        /* 이전,이후 투두 ID 셋팅*/

        ResponseDto.TodoDetail resultTodoDetails = sideTodoIdSettings(todoDetail);


        return resultTodoDetails;
    }

    /* 이전,이후 TODO ID 세팅 메소드*/
    private ResponseDto.TodoDetail sideTodoIdSettings (ResponseDto.TodoDetail settingData){
        List<Todo> memberTodoList = todoRepository.findByMemberIdOrderByCreateDateDesc(settingData.getMemberId());

        int currentIndex = memberTodoList.stream()
                .map(Todo::getId)
                .toList()
                .indexOf(settingData.getTodoId());
        NumberName numberName = new NumberName(memberTodoList.size()-1,currentIndex);

        if(memberTodoList.size() == HAS_SINGLE_TODO){
            return settingData;
        }

        if (currentIndex == FIRST_TODO_POSITION) {
            settingData.setNextTodoId(memberTodoList.get(numberName.NEXT_INDEX).getId());
        }

        else if (currentIndex == numberName.LAST_TODO_POSITION) {
            settingData.setPreviousTodoId(memberTodoList.get(numberName.PREVIOUS_INDEX).getId());

        } else {
            settingData.sideTodoIdSetting(
                    memberTodoList.get(numberName.NEXT_INDEX).getId(),
                    memberTodoList.get(numberName.PREVIOUS_INDEX).getId()
            );

        }

        return settingData;
    }

    /* Todo List */
    public ResponseDto.TodoList findTodos(){
        Pageable pageable = PageRequest.of(0,10,Sort.by(Sort.Direction.DESC,"viewCount"));

        Page<Todo> allTodos = todoRepository.findAll(pageable);

        Page<ServiceDto.AllTodosDto> pagingTodo = allTodos.map(todo -> new ServiceDto.AllTodosDto(todo));

        List<ServiceDto.TopTodo> topTodos =
                allTodos.stream().limit(5).map(topTodo -> new ServiceDto.TopTodo(topTodo)).collect(Collectors.toList());

        RedisDto.Members topMembers= getTopMembers();

        return new ResponseDto.TodoList(pagingTodo,topMembers.getMembers(),topTodos);
    }

    @Transactional
    public RedisDto.Members getTopMembers(){
        String cacheKey = "topMembers::top";
        RedisDto.Members cachedMembers = cacheManager.getCache("topMembers").get(cacheKey, RedisDto.Members.class);

        if (cachedMembers != null) {
            return cachedMembers;
        }

        List<Member> topMembers = memberRepository.findTop6ByOrderByTotalViewCountDesc();

        List<ServiceDto.TopMember> collect = topMembers.stream()
                .map(topMember -> new ServiceDto.TopMember(topMember))
                .collect(Collectors.toList());

        RedisDto.Members members = new RedisDto.Members(collect);
        cacheManager.getCache("topMembers").put(cacheKey, members);
        return members;
    }


    /* Todo 생성 */
    @Transactional
    public void createTodo(String title, String keyword, MemberAdapter loginMember){
        Optional<Keyword> existCheckKeyword = keywordRepository.findByName(keyword);
        Todo todo = null;

        if(existCheckKeyword.isPresent()){
            todo = new Todo(title,existCheckKeyword.get(),loginMember.getMember());
        }

        if(existCheckKeyword.isEmpty()){
            Keyword newKeyword =new Keyword(keyword);
            todo =new Todo(title,newKeyword,loginMember.getMember());
            keywordRepository.save(newKeyword);
        }
        todoRepository.save(todo);
    }


    /* Todo Posting 작성 및 수정 */
    @Transactional
    public void createTodoPost(Long todoId,String content){

        Optional<Todo> findResultTodo = todoRepository.findById(todoId);

        /* Dirty Checking*/
        findResultTodo.get().createTodoPost(content);

    }
}
