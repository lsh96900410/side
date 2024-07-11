package jpabook.jpashop.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {

    BAD_REQUEST("잘못된 요청입니다. 다시 한번 내용을 확인해주세요."),

    NOT_EXIST_MEMBER("존재하지 않는 회원 입니다. "),
    NOT_AUTHENTICATION_MEMBER("로그인 필수입니다. "),


    SELF_FOLLOW( "자기 자신을 팔로우 할 수 없습니다."),
    EXIST_FOLLOW("이미 팔로우 한 사용자입니다."),
    NOT_EXIST_FOLLOW("팔로우 한 기록이 없습니다."),

    NOT_EXIST_LIKE("좋아요를 하지 않았습니다."),
    EXIST_LIKE( "이미 좋아요를 했습니다."),
    SAME_USER_TODO( "본인 투두는 좋아요가 불가합니다."),
    NOT_EXIST_TODO( "관련 투두가 존재하지 않습니다."),

    NOT_EXIST_ALARM( "관련 알람이 존재하지 않습니다."),

    NOT_DELETE_OLD_FILE("이전 파일을 삭제하지 못했습니다."),
    FILE_UPLOAD_FAILED("업로드 파일을 저장하지 못했습니다. "),
    ERROR_CREATE_DIRECTORY("새로운 디렉토리를 생성하지 못했습니다.");


    private final String message;

}
