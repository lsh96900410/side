package jpabook.jpashop.dtos;

import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.todo.Todo;
import lombok.Data;

public class ElseDto {

    @Data
    public static class SameDevPosition {
        private Long id;
        private String otherName;
        private String developerPosition;
        private String uploadFile;

        public SameDevPosition(Member member){
            this.id= member.getId();
            this.otherName= member.getName();
            this.developerPosition=member.getDeveloperPosition();
            this.uploadFile=member.getFileLocation();
        }
    }

    @Data
    public static class SameKeyword {
        private Long todoId;
        private String memberName;
        private String todoTitle;
        private String uploadFile;
    }
}
