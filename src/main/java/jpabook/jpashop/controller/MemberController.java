package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){

        // form 객체 데이터도 같이 가져감
        if(result.hasErrors()) return "members/createMemberForm";


        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        // 엔티티 사용 -> 템플릿 엔진 렌더딩 + 엔티티 손댈게없음 필드 거의 다 필요 
        // API 만들때는 절대 엔티티를 넘기면 안됨.
        // API == 스펙 , EX) 엔티티에 필드 변경 -> API 스펙도 변함, => 불안전한 API 스펙 ;
        // 외부에는 엔티티를 절대 API로 노출시켜서는 안된다.
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        model.addAttribute("members",memberService.findMembers());
        return "members/memberList";
    }
}
