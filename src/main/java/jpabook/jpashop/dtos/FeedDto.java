package jpabook.jpashop.dtos;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedDto {
    /* Todo */
    private Long todoId;
    private String todoTitle;
    private int viewCount;
    private int likeCount;

    /* Member */
    private Long memberId;
    private String fileLocation;

    /* Keyword*/
    private String keywordName;


    @QueryProjection
    public FeedDto(Long todoId,String todoTitle,int viewCount, int likeCount, String keywordName, Long memberId, String fileLocation){
        this.todoId=todoId; this.todoTitle=todoTitle; this.viewCount=viewCount; this.likeCount=likeCount;

        this.memberId=memberId; this.fileLocation=fileLocation;

        this.keywordName=keywordName;
    }
}
