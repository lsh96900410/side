package jpabook.jpashop.dtos;

import static jpabook.jpashop.constant.NumberName.*;
import jpabook.jpashop.domain.follow.Follow;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.todo.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class ResponseDto {

    @Data
    public static class FindTodo {
        private Long id;
        private String title;
        private String content;
        private boolean completionStatus  ;
        private int viewCount;
        private int likeCount;
        private String keyword;

        public FindTodo(Todo todo){
            this.id = todo.getId();
            this.title=todo.getTitle();
            this.content=todo.getContent();
            this.viewCount=todo.getViewCount();
            this.likeCount=todo.getLikes().size();
            this.completionStatus=todo.isCompletionStatus();
            this.keyword=todo.getKeyword().getName();
        }

    }

    @Data
    @NoArgsConstructor
    public static class MemberFeed {

        /* Member Data */
        private Long memberId;
        private String name;
        private String developerPosition;
        private String career;

        private int totalFollowingCount;

        private int totalFollowerCount;

        private boolean isFollow = false;

        private String uploadLocation;

        /* Todo Data */
        List<ResponseDto.FindTodo> todos = new ArrayList<>();

        /* else Member */
        List<ElseDto.SameDevPosition> elseMembers = new ArrayList<>();

        /* else Todo */
        List<ElseDto.SameKeyword> elseTodos = new ArrayList<>();

        List<ServiceDto.TopMember> topMembers = new ArrayList<>();

        @Builder
        public ResponseDto.MemberFeed settingMemberData(Member member,int totalFollowingCount,int totalFollowerCount){
            this.memberId=member.getId();
            this.name=member.getName();
            this.developerPosition=member.getDeveloperPosition();
            this.career=member.getCareer();
            this.uploadLocation= member.getFileLocation();
            this.totalFollowingCount=totalFollowingCount;
            this.totalFollowerCount=totalFollowerCount;

            return this;
        }

        public void settingElseData(List<ElseDto.SameDevPosition> sameDevMembers,
                                    List<ResponseDto.FindTodo> todos,
                                    List<ServiceDto.TopMember> topMembers){
            elseMembers=sameDevMembers;
            this.todos=todos;
            this.topMembers=topMembers;
        }

        public void changFollowState(){
            this.isFollow=true;
        }

    }

    @Data
    public static class TodoDetail {

        private Long todoId;
        private String title;
        private String post;
        private String keywordName;
        private boolean isLike = false;
        private int viewCount;
        private int likeCount;
        private String profile;
        private Long keywordId;
        private Long memberId;
        private Long previousTodoId = NOT_EXIST_TODO_POSITION;
        private Long nextTodoId = NOT_EXIST_TODO_POSITION;
        private List<ServiceDto.TopTodo> topTodos=new ArrayList<>();

        /* previous,next 메소드 호출 전 생성자 */
        public TodoDetail(Todo todo,List<ServiceDto.TopTodo> topTodos){
            this.todoId=todo.getId();
            this.memberId=todo.getMember().getId();
            this.title=todo.getTitle();
            this.viewCount=todo.getViewCount();
            this.likeCount=todo.getLikes().size();
            this.post=todo.getContent();
            this.keywordName=todo.getKeyword().getName();
            this.keywordId=todo.getKeyword().getId();
            this.topTodos=topTodos;
            this.profile=todo.getMember().getFileLocation();
        }

        public void alreadyLikeState(){
            this.isLike=true;
        }

        public void sideTodoIdSetting(Long previousTodoId, Long nextTodoId){
            this.previousTodoId=previousTodoId;
            this.nextTodoId=nextTodoId;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodoList {
        /**
         *  투두 타이틀, 투두 키워드, 투두 아이디 들어간 List + 분야에서 가장 많이 읽은 TODO 타이틀, 키워드,TODO PK
         */
        private Page<ServiceDto.AllTodosDto> todos;

        private List<ServiceDto.TopMember> topMembers=new ArrayList<>();

        private List<ServiceDto.TopTodo> topTodos = new ArrayList<>();


    }
    @Data
    public static class Follows {
        private Long memberId;
        private String name;
        private String developerPosition;

        public Follows(Follow follow){
            this.memberId=follow.getFollower().getId();
            this.name=follow.getFollower().getName();
            this.developerPosition=follow.getFollower().getDeveloperPosition();
        }

    }

}
