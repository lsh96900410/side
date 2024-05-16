package jpabook.jpashop.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RequestDto {

    @Data
    public static class CreateTodo {
        private String todoTitle;

        private String keyword ;
    }

    @Data
    public static class CreateTodoPost {
        private Long id;

        private String postText;
    }

    @Data
    public static class Like {
        private Long todoId;
    }

    @Data
    public static class FollowDto {
        private Long followerId;

        private Long followingId;
    }

    @Data
    public static class Search {
        private String title;
        private String keyword;
        private String position;
        private String career;
    }
}
