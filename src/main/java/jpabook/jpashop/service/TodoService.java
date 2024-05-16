package jpabook.jpashop.service;

import static jpabook.jpashop.constant.NumberName.*;

import jpabook.jpashop.constant.ExceptionType;
import jpabook.jpashop.domain.keyword.Keyword;
import jpabook.jpashop.domain.keyword.KeywordRepository;
import jpabook.jpashop.domain.like.LikeRepository;
import jpabook.jpashop.domain.like.Likes;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.member.MemberRepository;
import jpabook.jpashop.domain.todo.Todo;
import jpabook.jpashop.domain.todo.TodoRepository;
import jpabook.jpashop.dtos.MemberAdapter;
import jpabook.jpashop.dtos.RequestDto;
import jpabook.jpashop.dtos.ResponseDto;
import jpabook.jpashop.dtos.ServiceDto;
import jpabook.jpashop.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        ResponseDto.TodoDetail todoDetail = new ResponseDto.TodoDetail(findTodo);

        /* 좋아요 기록 체크 */
        Optional<Likes> checkExistLike = likeRepository.findByMemberIdAndTodoId(loginMemberId, todoId);

        checkExistLike.ifPresent(alreadyExistLike -> todoDetail.alreadyLikeState());

        /* 자기 자신 투두는 ViewCount 증가 X */
        if(findTodo.getMember().getId()!=loginMemberId){
            findTodo.upViewCount();
            findTodo.getMember().upViewCount();
        }

        /* 이전,이후 투두 ID 셋팅*/
        ResponseDto.TodoDetail resultSideSetting = sideTodoIdSettings(todoDetail);

        return resultSideSetting;
    }

    /* 이전,이후 TODO ID 세팅 메소드*/
    private ResponseDto.TodoDetail sideTodoIdSettings (ResponseDto.TodoDetail settingData){
        List<Todo> memberTodoList = todoRepository.findByMemberIdOrderByCreateDateDesc(settingData.getMemberId());

        int currentIndex = memberTodoList.stream()
                .map(Todo::getId)
                .toList()
                .indexOf(settingData.getTodoId());

        final int LAST_TODO_POSITION = memberTodoList.size()-1;
        final int NEXT_INDEX = currentIndex+1;
        final int PREVIOUS_INDEX = currentIndex-1;


        if(memberTodoList.size() == HAS_SINGLE_TODO){
            settingData.setNextTodoId(NOT_EXIST_TODO_POSITION);
            settingData.setPreviousTodoId(NOT_EXIST_TODO_POSITION);
        }else {

            if (currentIndex == FIRST_TODO_POSITION) {
                settingData.setNextTodoId(memberTodoList.get(NEXT_INDEX).getId());
                settingData.setPreviousTodoId(NOT_EXIST_TODO_POSITION);
            } else if (currentIndex == LAST_TODO_POSITION) {
                settingData.setPreviousTodoId(memberTodoList.get(PREVIOUS_INDEX).getId());
                settingData.setNextTodoId(NOT_EXIST_TODO_POSITION);
            } else {
                settingData.setNextTodoId(memberTodoList.get(NEXT_INDEX).getId());
                settingData.setPreviousTodoId(memberTodoList.get(PREVIOUS_INDEX).getId());
            }
        }
        return settingData;
    }

    /* Todo List */
    public ResponseDto.TodoList findTodos(){
        List<Todo> allTodos = todoRepository.findAll();

        List<ServiceDto.AllTodosDto> findTodos =
                allTodos.stream().map(todo ->
                        new ServiceDto.AllTodosDto(todo)).collect(Collectors.toList());

        List<Member> getTopMembers = memberRepository.findTop6ByOrderByTotalViewCountDesc();

        List<ServiceDto.TopMember> topMembers =
                getTopMembers.stream().map(topMember ->
                        new ServiceDto.TopMember(topMember)).collect(Collectors.toList());


        List<Todo> top5Todos = todoRepository.findTop5ByOrderByViewCountDesc();

        List<ServiceDto.TopTodo> topTodos =
                top5Todos.stream().map(topTodo ->
                        new ServiceDto.TopTodo(topTodo)).collect(Collectors.toList());

        ResponseDto.TodoList responseTodos = new ResponseDto.TodoList(findTodos,topMembers,topTodos);

        return responseTodos;
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
