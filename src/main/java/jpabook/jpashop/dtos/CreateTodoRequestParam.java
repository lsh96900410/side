package jpabook.jpashop.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateTodoRequestParam {

    private String todoTitle;

    private List<String> hashtags= new ArrayList<>();
}
