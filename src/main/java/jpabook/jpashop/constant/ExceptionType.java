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
//    FIELD_VALIDATION(HttpStatus.BAD_REQUEST, "{0} 필드 : {1}"),
//    WRONG_PREVIOUS_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
//    EXISTING_PREVIOUS_PASSWORD(HttpStatus.BAD_REQUEST,"사용한 적이 있던 비밀번호입니다. 다른 비밀번호를 사용해주세요."),

//    DUPLICATE_USER(HttpStatus.BAD_REQUEST, "중복된 사용자가 존재합니다."),
//    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 Email 입니다."),
//    CANNOT_DELETE_ONESELF(HttpStatus.BAD_REQUEST, "자신의 계정은 삭제할 수 없습니다."),
//
//    BAN_USER(HttpStatus.FORBIDDEN, "차단된 사용자입니다. 관리자에게 문의해주세요."),
//    BAN_USER_TEMP(HttpStatus.FORBIDDEN, "일시적으로 접근이 제한된 사용자입니다. {0} 이후에 시도해주세요."),
//
//    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다."),
//    NOT_EXIST_TOKEN_USERNAME(HttpStatus.UNAUTHORIZED, "사용자 정보가 없습니다. : {0}"),
//    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다. 재인증이 필요합니다."),
//    REFRESH_TOKEN_NOT_EXIST(HttpStatus.UNAUTHORIZED, "토큰 정보가 없습니다."),

//    ONLY_AUTHOR_ACCESS(HttpStatus.BAD_REQUEST, "작성자만 수정/삭제 가능합니다."),
//
//    NOT_EXIST_COMMENT(HttpStatus.BAD_REQUEST, "댓글이 존재하지 않습니다."),
//
//
//
//