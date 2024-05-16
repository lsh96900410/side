package jpabook.jpashop.service;

import jpabook.jpashop.constant.ExceptionType;
import jpabook.jpashop.domain.alarm.Alarm;
import jpabook.jpashop.domain.alarm.AlarmRepository;
import jpabook.jpashop.domain.alarm.AlarmType;
import jpabook.jpashop.domain.like.LikeRepository;
import jpabook.jpashop.domain.like.Likes;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.todo.Todo;
import jpabook.jpashop.domain.todo.TodoRepository;
import jpabook.jpashop.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final TodoRepository todoRepository;
    private final AlarmRepository alarmRepository;

    @Transactional
    public void likeTodo(Member member,Long todoId){

        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new CustomException(ExceptionType.NOT_EXIST_TODO));

        /* 본인 투두 좋아요 불가 */
        if(todo.getMember().getId().equals(member.getId())){
            throw new CustomException(ExceptionType.SAME_USER_TODO);
        }

        Optional<Likes> byMemberIdAndTodoId = likeRepository.findByMemberIdAndTodoId(member.getId(), todoId);

        /* 중복 좋아요 불가 */
        byMemberIdAndTodoId.ifPresent(T -> {
            throw new CustomException(ExceptionType.EXIST_LIKE);});
        
        Likes like= new Likes(member,todo);

        likeRepository.save(like);

        /* 알람 설정*/

        Alarm alarm=new Alarm(member,todo.getMember(), AlarmType.LIKE);

        alarmRepository.save(alarm);
    }

    @Transactional
    public void unLikeTodo(Member member,Long todoId){

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->
                        new CustomException(ExceptionType.NOT_EXIST_TODO));

       Likes like = likeRepository.findByMemberIdAndTodoId(member.getId(), todoId)
               .orElseThrow(() ->
                       new CustomException(ExceptionType.NOT_EXIST_LIKE));

       likeRepository.delete(like);

        /* 알람 삭제 */

        Alarm alarm =
                alarmRepository.findByToMemberIdAndFromMemberIdAndAlarmType(todo.getMember().getId(), member.getId(), AlarmType.LIKE)
                        .orElseThrow(() ->
                                new CustomException(ExceptionType.NOT_EXIST_ALARM));

        alarmRepository.delete(alarm);


    }






}
