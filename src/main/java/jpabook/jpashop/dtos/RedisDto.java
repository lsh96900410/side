package jpabook.jpashop.dtos;

import jpabook.jpashop.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RedisDto {

    @Getter
    @NoArgsConstructor
    public static class Members implements Serializable {
        private List<ServiceDto.TopMember> members = new ArrayList<>();

        public Members(List<ServiceDto.TopMember> members){
            this.members=members;
        }
    }

}
