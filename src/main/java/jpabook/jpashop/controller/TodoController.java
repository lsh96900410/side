package jpabook.jpashop.controller;

import jpabook.jpashop.dtos.*;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/{todoId}")
    public String details(@PathVariable Long todoId, Model model, @AuthenticationPrincipal MemberAdapter memberAdapter){

        long loginMemberId = memberAdapter.getMember().getId();

        ResponseDto.TodoDetail todoDetail = todoService.todoDetails(todoId,loginMemberId);

        model.addAttribute("todo",todoDetail);
        return "todo/detail";
    }

    @GetMapping()
    public String list(Model model){
        //long startNotCache = System.currentTimeMillis();
        ResponseDto.TodoList todos = todoService.findTodos();
        model.addAttribute("todos",todos);
        //long endNotCache = System.currentTimeMillis();
        //System.out.println("@@@ 캐시 테스팅 : " + (endNotCache - startNotCache) + " ms");
        return "todo/list";
    }

    @PostMapping
    public String createTodo(@AuthenticationPrincipal MemberAdapter loginMember,@RequestParam String title,@RequestParam String keyword ){
            todoService.createTodo(title,keyword,loginMember);
            return "redirect:/members/"+loginMember.getMember().getId();
    }

    @PostMapping("/content")
    public String createTodoPost(@RequestParam Long todoId,@RequestParam String todoContent ){
        todoService.createTodoPost(todoId, todoContent);
        /* TODO - ChatGPT API 통해서 WHAT,WHY,HOW 질문 생성해서 보내기 */

        return "redirect:/todo/"+todoId;
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponseDto> searchTodo(@ModelAttribute RequestDto.Search search){

        List<ServiceDto.AllTodosDto> searchResultTodos = todoService.searchByTitle(search);

        HttpResponseDto responseDto = new HttpResponseDto();
        responseDto.setData(searchResultTodos);

        if (searchResultTodos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // 검색 결과가 없는 경우
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseDto); // 검색 결과가 있는 경우
    }




}
