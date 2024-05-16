package jpabook.jpashop.controller;

import jpabook.jpashop.dtos.RequestDto;
import jpabook.jpashop.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public @ResponseBody ResponseEntity follow(@RequestBody RequestDto.FollowDto requestFollowData){
        followService.follow(requestFollowData);

        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @DeleteMapping()
    public @ResponseBody ResponseEntity unFollow(@RequestBody RequestDto.FollowDto requestUnFollowData){
        followService.unFollow(requestUnFollowData);

        return ResponseEntity.status(HttpStatus.OK).body("ok");

    }

    @GetMapping("/following")
    public void getFollowings(@PathVariable Long memberId){

        followService.getFollowings(memberId);
    }

    @GetMapping("/followers")
    public void getFollowers(@PathVariable Long memberId){

        followService.getFollowers(memberId);
    }
}
