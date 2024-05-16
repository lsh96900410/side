package jpabook.jpashop.domain.follow;

import jpabook.jpashop.domain.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    List<Follow> findByFollowerId(Long followerId);

    List<Follow> findByFollowingId(Long followingId);

    int countByFollowerId(Long followerId);

    int countByFollowingId(Long followingId);

    Optional<Follow> findByFollowingIdAndFollowerId(Long followingId, Long id);

}
