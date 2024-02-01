package jpabook.jpashop.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jpabook.jpashop.config.auth.PrincipalDetails;
import jpabook.jpashop.dto.MemberForm;
import jpabook.jpashop.dto.MemberLogin;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm" , new MemberForm());
        return "members/createMemberForm";
    }

    @GetMapping("/members/login")
    public String loginForm(Model model){
        model.addAttribute("loginForm",new MemberLogin());
        return "members/login";
    }

    @PostMapping("/members/login")
    public String login(@ModelAttribute MemberLogin memberLogin, HttpSession session,Model model){
        System.out.println("members/login !@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@!");
        try {
            String result = memberService.login(memberLogin);
            session.setAttribute("id",memberLogin.getUsername());
            session.setAttribute("role",result);
            return "redirect:/";
        } catch (UsernameNotFoundException e) {
            model.addAttribute("loginForm",memberLogin);
            return "/members/login";
        }catch (BadCredentialsException e){
            model.addAttribute("loginForm",memberLogin);
            return "/members/login";
        }catch (Exception e ){
            return "redirect:/";
        }
    }
    @GetMapping("/members/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){

        // form 객체 데이터도 같이 가져감
        if(result.hasErrors()) return "members/createMemberForm";

        memberService.join(form);

        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/members")
    public String list(@AuthenticationPrincipal PrincipalDetails psd, Model model){
        System.out.println("@@@@ Members.. Admin 이여햐마 "+psd.getAuthorities().contains("ROLE_ADMIN"));
        model.addAttribute("members",memberService.findMembers());
        return "members/memberList";
    }

    @GetMapping("/members/{memberId}")
    public String one(@PathVariable("memberId") Long id , Model model){
        model.addAttribute("member",memberService.findOne(id));
        return "members/member";
    }
}
