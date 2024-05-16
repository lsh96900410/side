package jpabook.jpashop.dtos;

import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.todo.Todo;
import lombok.Data;

public class ElseDto {

    @Data
    public static class SameDevPositionOtherMember{
        private Long id;

        private String otherName;

        private String developerPosition;
        private String uploadFile;

        public SameDevPositionOtherMember(Member member){
            this.id= member.getId();
            this.otherName= member.getName();
            this.developerPosition=member.getDeveloperPosition();
            this.uploadFile=member.getFileLocation();
        }
    }
    @Data
    public static class SameKeywordOtherTodo{
        private Long todoId;

        private String memberName;

        private String todoTitle;

        private String uploadFile;
        public SameKeywordOtherTodo(Todo todo){
            this.todoId=todo.getId();
            this.memberName=todo.getMember().getName();
            this.todoTitle=todo.getTitle();
            this.uploadFile=todo.getMember().getFileLocation();
        }
    }
}
