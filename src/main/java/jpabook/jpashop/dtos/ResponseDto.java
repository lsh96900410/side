package jpabook.jpashop.dtos;

import jpabook.jpashop.domain.alarm.AlarmType;
import jpabook.jpashop.domain.follow.Follow;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.todo.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    public static class FindTodos {
        private Long id;
        private String title;
        private boolean completionStatus;
        private String keyword;
        private int likeCount;

        public FindTodos(Todo todo){
            this.id = todo.getId();
            this.title=todo.getTitle();
            this.completionStatus=todo.isCompletionStatus();
            this.keyword=todo.getKeyword().getName();
            this.likeCount = todo.getLikes().size();
        }
    }

    public static class FindAlarms extends ResponseDto{
        private String fromMemberName;

        private AlarmType alarmType;

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
        List<ElseDto.SameDevPositionOtherMember> elseMembers = new ArrayList<>();

        /* else Todo */
        List<ElseDto.SameKeywordOtherTodo> elseTodos = new ArrayList<>();

        List<ServiceDto.TopMember> topMembers = new ArrayList<>();

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

        public ResponseDto.MemberFeed settingTodosData(List<ResponseDto.FindTodo> dtos){
            this.todos=dtos;
            return this;
        }

        public void settingElseMembersData(List<ElseDto.SameDevPositionOtherMember> dtos){
            this.elseMembers=dtos;
        }

        public void settingElseKeywordData(List<ElseDto.SameKeywordOtherTodo> dtos){
            this.elseTodos=dtos;
        }

        public void changFollowState(){
            this.isFollow=true;
        }

    }

    @Data
    public static class TodoDetail {
        /**
         *  투두 타이틀, 투두 포스트, 키워드 이름, 키워드 pk , 멤버 pk, 투두 pk, 좋아요 수 , 클릭 수,  이전,이후 투두 pk
         */

        private Long todoId;

        private String title;

        private String post;

        private String keywordName;

        private boolean isLike = false;

        private int viewCount;

        private int likeCount;

        private Long keywordId;

        private Long memberId;

        private Long previousTodoId;

        private Long nextTodoId;

        /* previous,next 메소드 호출 전 생성자 */
        public TodoDetail(Todo todo){
            this.todoId=todo.getId();
            this.memberId=todo.getMember().getId();
            this.title=todo.getTitle();
            this.viewCount=todo.getViewCount();
            this.likeCount=todo.getLikes().size();
            this.post=todo.getContent();
            this.keywordName=todo.getKeyword().getName();
            this.keywordId=todo.getKeyword().getId();
        }

        public void alreadyLikeState(){
            this.isLike=true;
        }


    }

    @Data
    @AllArgsConstructor
    public static class TodoList {
        /**
         *  투두 타이틀, 투두 키워드, 투두 아이디 들어간 List + 분야에서 가장 많이 읽은 TODO 타이틀, 키워드,TODO PK
         */
        private List<ServiceDto.AllTodosDto> todos= new ArrayList<>();

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

    @Data
    public static class Followings {
        private Long memberId;
        private String name;
        private String developerPosition;

        public Followings(Follow follow){
            this.memberId=follow.getFollowing().getId();
            this.name=follow.getFollowing().getName();
            this.developerPosition=follow.getFollowing().getDeveloperPosition();
        }

    }

}
