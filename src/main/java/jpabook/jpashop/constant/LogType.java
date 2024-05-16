package jpabook.jpashop.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogType {


    SUCCESS_UPLOAD_NEW_FILE(" 새로운 파일 업로드 완료 "),
    SUCCESS_DELETE_OLD_FILE(" 이전 이미지 삭제 완료 "),
    ERROR_DELETING_OLD_FILE(" 이전 이미지 삭제 불가");




    private final String message;

}
