package jpabook.jpashop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jpabook.jpashop.constant.ExceptionType;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.dtos.FormData;
import jpabook.jpashop.dtos.MemberAdapter;
import jpabook.jpashop.dtos.ResponseDto;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final TodoService todoService;


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/todo";
    }


    @PostMapping("/upload")
    public @ResponseBody ResponseEntity uploadMemberImage(@AuthenticationPrincipal MemberAdapter memberAdapter, @RequestParam("file")MultipartFile file){


        memberService.uploadProfile(memberAdapter.getMember().getId(), file);

        return ResponseEntity.status(HttpStatus.OK).body(" 업로드 성공 ~~ ");
    }


    @GetMapping("/{memberId}")
    public String myHome(@AuthenticationPrincipal MemberAdapter memberAdapter, @PathVariable Long memberId, Model model)  {

        ResponseDto.MemberFeed memberFeed = memberService.find(memberId,memberAdapter.getMember().getId());

        model.addAttribute("feedData",memberFeed);
        return "members/myHome";
    }

//    @PostMapping
//    public String join(@Valid FormData.Join joinForm, BindingResult result){
//        if(result.hasErrors()){
//            return "members/joinForm";
//        }
//        memberService.join(joinForm);
//
//        return "redirect:/sign";
//    }


    @GetMapping("/sign")
    public String loginForm(Model model) {
        long[] count = memberService.count();
        model.addAttribute("count",count);
        model.addAttribute("loginForm",new FormData.Login());
        model.addAttribute("joinForm",new FormData.Join());
        return "members/sign-in";
    }

}
