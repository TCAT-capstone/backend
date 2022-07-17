package return_a.tcat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.Member;
import return_a.tcat.oauth.*;
import return_a.tcat.repository.MemberRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OAuthService {

    private final List<SocialOauth> socialOauthList;
    private final GoogleOauth googleOauth;
    private final HttpServletResponse response;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    public void request(OAuthConstant.SocialLoginType socialLoginType) throws IOException {
        String redirectURL;
        switch (socialLoginType) {
            case GOOGLE: {
                //각 소셜 로그인을 요청하면 소셜로그인 페이지로 리다이렉트 해주는 프로세스이다.
                redirectURL = googleOauth.getOauthRedirectURL();
            }
            break;
            default: {
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }

        }

        response.sendRedirect(redirectURL);
    }

    @Transactional
    public GetSocialOAuthRes oAuthLogin(OAuthConstant.SocialLoginType socialLoginType, String code) throws JsonProcessingException {

        //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
        ResponseEntity<String> accessTokenResponse = googleOauth.requestAccessToken(code);
        //응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
        GoogleOAuthToken oAuthToken = googleOauth.getAccessToken(accessTokenResponse);

        ResponseEntity<String> userInfoResponse = googleOauth.requestUserInfo(oAuthToken);
        GoogleUser googleUser = googleOauth.getUserInfo(userInfoResponse);

        String email = googleUser.getEmail();
        String memberImg = googleUser.getPicture();
        List<Member> findMembers = memberRepository.findByEmails(email);

        if (findMembers.isEmpty()) {
            //새로운 회원이라면
            Member member = Member.builder()
                    .homeId(null)
                    .name("익명") //미정일시 익명처리
                    .bio(null)
                    .memberImg(memberImg)
                    .email(email)
                    .provider("google")
                    .likeCount(0)
                    .ticketCount(0)
                    .build();
            memberRepository.save(member);
            //회원가입을 시킨다.

        }
        //그 후 바로 로그인 시키기
        Member member = memberRepository.findByEmail(email);
        String jwtToken = jwtService.createJwt(member.getId());
        //액세스 토큰과 jwtToken, 이외 정보들이 담긴 자바 객체를 다시 전송한다.
        GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(jwtToken,
                member.getId(), member.getHomeId(), member.getName(), member.getMemberImg(),
                oAuthToken.getAccess_token(), oAuthToken.getToken_type());
        return getSocialOAuthRes;

    }

}
