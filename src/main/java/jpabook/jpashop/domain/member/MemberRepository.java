package jpabook.jpashop.domain.member;

import jpabook.jpashop.domain.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    public Member findByEmail(String email);

    public Optional<Member> findByUsername(String name);

    public List<Member> findByDeveloperPosition(String developerPosition);

    List<Member> findTop6ByOrderByTotalViewCountDesc();

    long count();

}
