package jpabook.jpashop.dtos;

import jpabook.jpashop.domain.alarm.Alarm;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.todo.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ServiceDto {

    @Data
    public static class FindMember {
        private Long id;

        private String name;

        private String developerPosition;

        private String career;

        private int totalFollowingCount;

        private int totalFollowerCount;

        public FindMember(Member member){
            this.id= member.getId();
            this.name=getName();
            this.developerPosition=member.getDeveloperPosition();
            this.career=member.getCareer();
            this.totalFollowingCount=member.getFollowings().size();
            this.totalFollowerCount=member.getFollows().size();
        }
    }

    @Data
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
            this.likeCount=todo.getLikes().size();
            this.profileImage = todo.getMember().getFileLocation();
        }
    }

    @Data
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
    public static class TopTodo{
        private Long todoId;
        private String title;
        private String keyword;

        public TopTodo(Todo todo){
            this.todoId=todo.getId();
            this.title=todo.getTitle();
            this.keyword=todo.getKeyword().getName();
        }
    }

    @Data
    public static class GetAlarm{
        private Long memberId;
        private String memberName;
        private String message;
        private String image;
        private String todoTitle;
        private Long todoId;

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
