package jpabook.jpashop.controller;

import jakarta.servlet.http.HttpSession;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.rmi.AccessException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @RequestMapping("/")
    public String home(){
        return "home";
    }


}
