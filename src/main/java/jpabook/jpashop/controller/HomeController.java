package jpabook.jpashop.controller;

import jakarta.servlet.http.HttpSession;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @RequestMapping("/")
    public String home(HttpSession session){
        System.out.println((String) session.getAttribute("role"));
        System.out.println((String) session.getAttribute("id"));
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ home Controller");
        return "home";
    }
}
