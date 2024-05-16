package jpabook.jpashop.constant;

import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class NumberName {

    public static final int FIRST_TODO_POSITION = 0;

    public static final Long NOT_EXIST_TODO_POSITION = -1L;

    public static final int HAS_SINGLE_TODO = 1;

    public static final String FILE_UPLOAD_FOLDER = "memberProfileUpload/";

    public static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    public static final String CREATE_NEW_DIRECTORY = "경로에 디렉토리가 없어 새로 생성했습니다.";

}
