package jpabook.jpashop.dtos;

import jpabook.jpashop.domain.alarm.Alarm;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.todo.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ServiceDto {

    @Data
    @NoArgsConstructor
    public static class AllTodosDto{
        private Long todoId;

        private String todoTitle;

        private String keyword;

        private int viewCount;

        private int likeCount;

        private String profileImage;
        public AllTodosDto(Todo todo){
            this.todoId=todo.getId();
            this.todoTitle=todo.getTitle();
            this.keyword=todo.getKeyword().getName();
            this.viewCount=todo.getViewCount();
            this.likeCount=todo.getLikeCount();
            this.profileImage = todo.getMember().getFileLocation();
        }

        public AllTodosDto(FeedDto feedDto){
            this.todoId=feedDto.getTodoId();
            this.todoTitle=feedDto.getTodoTitle();
            this.keyword=feedDto.getKeywordName();
            this.viewCount=feedDto.getViewCount();
            this.likeCount=feedDto.getLikeCount();
            this.profileImage=feedDto.getFileLocation();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopMember{
        private Long memberId;
        private String name;
        private String developerPosition;
        private String profileImage;

        public TopMember(Member member){
            this.memberId=member.getId();
            this.name=member.getName();
            this.developerPosition=member.getDeveloperPosition();
            this.profileImage= member.getFileLocation();
        }
    }

    @Data
    @NoArgsConstructor
    public static class TopTodo{
        private Long todoId;
        private String title;
        private String keyword;
        private String image;
        public TopTodo(Todo todo){
            this.todoId=todo.getId();
            this.title=todo.getTitle();
            this.keyword=todo.getKeyword().getName();
            this.image=todo.getMember().getFileLocation();
        }

        public TopTodo(FeedDto feedDto){
            this.todoId=feedDto.getTodoId();
            this.title=feedDto.getTodoTitle();
            this.keyword=feedDto.getKeywordName();
            this.image=feedDto.getFileLocation();
        }
    }

    @Data
    public static class GetAlarm{
        private Long memberId;
        private String memberName;
        private String message;
        private String image;

        public GetAlarm(Alarm alarm){
            this.memberId=alarm.getFromMember().getId();
            this.memberName=alarm.getFromMember().getName();
            this.image=alarm.getFromMember().getFileLocation();
            
            if(alarm.getAlarmType().getMessage()=="좋아요"){
                this.message="님이 회원님의 목표를 좋아합니다.";
            }else{
                this.message = "님이 회원님을 팔로우했습니다.";
            }
        }

    }


}
