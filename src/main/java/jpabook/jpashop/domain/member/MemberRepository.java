package jpabook.jpashop.domain.member;

import jpabook.jpashop.domain.todo.Todo;
import jpabook.jpashop.dtos.ServiceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    public Member findByEmail(String email);

    public Optional<Member> findByUsername(String name);

    public List<Member> findTop5ByDeveloperPosition(String developerPosition);

    List<Member> findTop6ByOrderByTotalViewCountDesc();

    Boolean existsByUsername(String username);
    long count();

//    @Query("SELECT new jpabook.jpashop.dtos.ServiceDto.TopMember(m.id, m.name, m.developerPosition, m.fileLocation) FROM Member m ORDER BY m.totalViewCount DESC")
//    List<ServiceDto.TopMember> findTop6MembersByTotalViewCounts();

}
