package jpabook.jpashop.domain.todo;

import jpabook.jpashop.domain.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long>,TodoCustomRepository {

    public List<Todo> findByMemberIdOrderByCreateDateDesc(Long memberId);

    public List<Todo> findByKeywordId(Long keywordId);

    List<Todo> findTop5ByOrderByViewCountDesc();

    long count();

}
