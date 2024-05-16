package jpabook.jpashop.controller;

import jpabook.jpashop.dtos.ResponseDto;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final TodoService todoService;

    @RequestMapping("/")
   public String home(Model model){
        ResponseDto.TodoList todos = todoService.findTodos();

        model.addAttribute("todos",todos);
        return "todo/list";
   }


}
