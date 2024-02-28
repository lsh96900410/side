package jpabook.jpashop.config.oauth;

import jpabook.jpashop.config.auth.PrincipalDetails;
import jpabook.jpashop.config.oauth.provider.FacebookUserInfo;
import jpabook.jpashop.config.oauth.provider.GoogleUserInfo;
import jpabook.jpashop.config.oauth.provider.NaverUserInfo;
import jpabook.jpashop.config.oauth.provider.OAuth2UserInfo;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	@Transactional
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("principalOauth2UserService loadUser 메쏘드" + userRequest.getClientRegistration().getRegistrationId());

		OAuth2User oAuth2User = super.loadUser(userRequest);
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo=new GoogleUserInfo(oAuth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			oAuth2UserInfo=new FacebookUserInfo(oAuth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			oAuth2UserInfo=new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
		}else {
		}
		String password = bCryptPasswordEncoder.encode("skydog");
		Member findMember = memberRepository.findByName(oAuth2UserInfo.getEmail());

		if(findMember==null) {
			Member member=new Member(oAuth2UserInfo,password);
			memberRepository.save(member);
			return new PrincipalDetails(member);
		}
		return new PrincipalDetails(findMember, oAuth2User.getAttributes());
	}
}
