package jpabook.jpashop.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByMemberIdAndTodoId(Long memberId,Long todoId);
}
