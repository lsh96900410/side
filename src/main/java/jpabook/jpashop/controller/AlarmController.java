package jpabook.jpashop.controller;

import jpabook.jpashop.dtos.HttpResponseDto;
import jpabook.jpashop.dtos.MemberAdapter;
import jpabook.jpashop.dtos.ServiceDto;
import jpabook.jpashop.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;


    @GetMapping
    public ResponseEntity getAlarm(@AuthenticationPrincipal MemberAdapter loginMember){
        List<ServiceDto.GetAlarm> alarms = alarmService.findAlarms(loginMember.getMember().getId());

        HttpResponseDto responseDto = new HttpResponseDto();

        responseDto.setData(alarms);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
