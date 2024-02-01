package jpabook.jpashop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private String role;
    private boolean result;
}
