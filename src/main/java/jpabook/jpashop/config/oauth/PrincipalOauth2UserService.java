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
		OAuth2User oAuth2User = super.loadUser(userRequest);
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("google");
			oAuth2UserInfo=new GoogleUserInfo(oAuth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			System.out.println("facebook");
			oAuth2UserInfo=new FacebookUserInfo(oAuth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			System.out.println("naver");
			oAuth2UserInfo=new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
		}else {
			 System.out.println("sss");
		}
		String password = bCryptPasswordEncoder.encode("skydog");
		String username=oAuth2UserInfo.getProvider()+"_"+oAuth2UserInfo.getProviderId();
		Member findMember = memberRepository.findByName(username);

		if(findMember==null) {
			Member member=new Member(oAuth2UserInfo,password);
			memberRepository.save(member);
		}else {
		}
		return new PrincipalDetails(findMember, oAuth2User.getAttributes());
	}
}
