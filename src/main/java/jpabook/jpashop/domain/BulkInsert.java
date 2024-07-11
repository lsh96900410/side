package jpabook.jpashop.domain;

import jpabook.jpashop.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BulkInsert {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Member> postList){
        String sql = "INSERT INTO post_table (post_title, post_content)"+
                "VALUES (?, ?)";

//        jdbcTemplate.batchUpdate(sql,
//                postList,
//                postList.size(),
//                (PreparedStatement ps, Post post) -> {
//                    ps.setString(1, post.getPostTitle());
//                    ps.setString(2, post.getPostContent());
//                });
    }
}
