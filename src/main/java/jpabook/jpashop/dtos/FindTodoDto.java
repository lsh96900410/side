package jpabook.jpashop.dtos;

import lombok.Data;

@Data
public class FindTodoDto {

    private String text;

    private boolean complete;
}
