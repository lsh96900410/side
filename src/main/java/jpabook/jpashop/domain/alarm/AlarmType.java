package jpabook.jpashop.domain.alarm;

import lombok.Getter;

@Getter
public enum AlarmType {

    LIKE("좋아요"), FOLLOW("팔로우");

    AlarmType(String message) {
        this.message = message;
    }

    private String message;

}
