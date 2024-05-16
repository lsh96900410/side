package jpabook.jpashop.domain.todo;

import jpabook.jpashop.dtos.RequestDto;

import java.util.List;

public interface TodoCustomRepository {

    List<Todo> searchTodoByTitle(RequestDto.Search search);
}
