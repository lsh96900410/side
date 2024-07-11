package jpabook.jpashop.constant;

import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class NumberName {

    public static final String FILE_UPLOAD_FOLDER = "memberProfileUpload/";

    public static final int FIRST_TODO_POSITION = 0;

    public static final Long NOT_EXIST_TODO_POSITION = -1L;

    public static final int HAS_SINGLE_TODO = 1;


    public final int LAST_TODO_POSITION ;

    public final int NEXT_INDEX;

    public final int PREVIOUS_INDEX;

    public NumberName(int lastTodoPosition,int currentIndex){
        this.LAST_TODO_POSITION=lastTodoPosition;
        this.NEXT_INDEX = currentIndex - 1;
        this.PREVIOUS_INDEX = currentIndex + 1;
    }

}
