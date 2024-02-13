package jpabook.jpashop.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jpabook.jpashop.config.auth.PrincipalDetails;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.MemberForm;
import jpabook.jpashop.dto.MemberLogin;
import jpabook.jpashop.dto.MemberSearch;
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

import java.util.List;

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
        try {
            memberService.login(memberLogin);
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

        if(result.hasErrors()) return "members/createMemberForm";

        memberService.join(form);

        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/members")
    public String list(@AuthenticationPrincipal PrincipalDetails psd,
                       @ModelAttribute("memberSearch") MemberSearch memberSearch, Model model){
        model.addAttribute("members",memberService.searchMembers(memberSearch));
        return "members/memberList";
    }

    @GetMapping("/members/search")
    public @ResponseBody List<Member> list(@ModelAttribute("memberSearch") MemberSearch memberSearch){
        return memberService.searchMembers(memberSearch);
    }
    @GetMapping("/members/{memberId}")
    public String one(@PathVariable("memberId") Long id , Model model){
        model.addAttribute("member",memberService.findOne(id));
        return "members/member";
    }
}
