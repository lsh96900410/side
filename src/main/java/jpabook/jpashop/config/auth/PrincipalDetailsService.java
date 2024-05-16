package jpabook.jpashop.config.auth;

import jpabook.jpashop.domain.member.MemberRepository;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.dtos.MemberAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> findMember = memberRepository.findByUsername(username);
        if(findMember!=null){
            return new MemberAdapter(findMember.get());
        }
        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다. "+username);
    }
}
