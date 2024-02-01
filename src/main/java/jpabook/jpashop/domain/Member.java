package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.config.oauth.provider.OAuth2UserInfo;
import jpabook.jpashop.dto.MemberForm;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String username;

    private String password;

    private String email;

    private String provider;

    private String providerId;

    @Embedded
    private Address address;

    private String role;

    @Builder
    public Member(MemberForm form){
        this.username=form.getUsername();
        this.password=form.getPassword();
        this.address=form.formToAddress();
        this.role="ROLE_USER";
    }

    @Builder
    public Member(OAuth2UserInfo info,String password){
        this.email=info.getEmail();
        this.password=password;
        this.username=info.getProvider()+"_"+info.getProviderId();
        this.password=password;
        this.role="ROLE_USER";
        this.provider=info.getProvider();
        this.providerId=info.getProviderId();
    }

}
