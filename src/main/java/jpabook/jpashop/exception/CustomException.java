package jpabook.jpashop.exception;

import jpabook.jpashop.constant.ExceptionType;

import java.util.Map;

public class CustomException extends RuntimeException{

    private ExceptionType type = ExceptionType.BAD_REQUEST;
    private String message;

    public CustomException(){
        super(ExceptionType.BAD_REQUEST.getMessage());
    }

    public CustomException(String message){
        super(message);
    }

    public CustomException(ExceptionType type, String... values) {
        this.type = type;
        this.message = replaceText(type.getMessage(), values);
    }

    @Override
    public String getMessage() {
        return message == null ? super.getMessage() : message;
    }



    /* 공부 */
    private String replaceText(String text, String... values) {

        String replaceText = text;
        for (int i = 0; i < values.length; i++) {
            replaceText = replaceText.replaceAll("\\{" + i + "\\}" , values[i]);
        }

        return replaceText;
    }

    private String replaceText(String text, Map<String, String> map) {

        String replaceText = text;
        for (String key : map.keySet()) {
            replaceText = replaceText.replaceAll("\\{" + key + "\\}" , map.get(key));
        }

        return replaceText;
    }

}
