package jpabook.jpashop.service;

import jpabook.jpashop.domain.alarm.Alarm;
import jpabook.jpashop.domain.alarm.AlarmRepository;
import jpabook.jpashop.dtos.ServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;


    public List<ServiceDto.GetAlarm> findAlarms(Long memberId){
        List<Alarm> alarmsData = alarmRepository.findTop5ByToMemberIdOrderByCreateDateDesc(memberId);

        List<ServiceDto.GetAlarm> alarms =
                alarmsData.stream().map(alarm -> new ServiceDto.GetAlarm(alarm)).collect(Collectors.toList());

        return alarms;

    }

}
