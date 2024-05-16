package jpabook.jpashop.controller;

import jpabook.jpashop.dtos.MemberAdapter;
import jpabook.jpashop.dtos.RequestDto;
import jpabook.jpashop.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public @ResponseBody ResponseEntity likeTodo(@AuthenticationPrincipal MemberAdapter memberAdapter, @RequestBody RequestDto.Like like){
        likeService.likeTodo(memberAdapter.getMember(),like.getTodoId());

        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @DeleteMapping("/{todoId}")
    public @ResponseBody ResponseEntity unLike(@AuthenticationPrincipal MemberAdapter memberAdapter,@PathVariable Long todoId){


        likeService.unLikeTodo(memberAdapter.getMember(),todoId);

        return ResponseEntity.status(HttpStatus.OK).body(" ok");
    }


}
