package jpabook.jpashop.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;

@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler({AccessDeniedException.class})
    public String exception (){
        System.out.println("익셉션핸들러 발동 ");
        return "403";
    }

}
