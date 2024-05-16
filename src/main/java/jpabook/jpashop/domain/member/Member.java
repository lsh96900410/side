package jpabook.jpashop.domain.member;

import jakarta.persistence.*;
import jpabook.jpashop.config.oauth.provider.OAuth2UserInfo;
import jpabook.jpashop.domain.follow.Follow;
import jpabook.jpashop.domain.like.Likes;
import jpabook.jpashop.domain.todo.Todo;
import jpabook.jpashop.dtos.FormData;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String username;

    private String password;

    private String email;

    private String name;

    private String developerPosition;

    private String career;

    private String fileLocation;

    private int totalViewCount=0;

    private LocalDate signUpDate;


    /* Security */

    private String provider;

    private String providerId;

    private String role;

    /* 연관 관계 */

    @OneToMany(mappedBy = "member")
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<Follow> follows = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Todo> todos = new ArrayList<>();


    public Member encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
        return this;
    }



    @Builder
    public Member(OAuth2UserInfo info, String password){
        this.email=info.getEmail();
        this.password=password;
        this.username=info.getEmail();
        this.password=password;
        this.role="ROLE_USER";
        this.provider=info.getProvider();
        this.providerId=info.getProviderId();
    }

    public Member(FormData.Join dto,PasswordEncoder passwordEncoder){
        this.username=dto.getUsername();
        this.password = passwordEncoder.encode(dto.getPassword());
        this.name=dto.getName();
        this.email=dto.getEmail();
        this.career=dto.getCareer();
        this.developerPosition=dto.getDeveloperPosition();
        this.role="ROLE_USER";
    }

    public void uploadProfileImageLocation(String fileLocation){
        this.fileLocation = fileLocation;
    }

    public void upViewCount(){
        this.totalViewCount++;
    }

}
