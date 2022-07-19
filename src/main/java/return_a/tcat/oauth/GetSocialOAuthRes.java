package return_a.tcat.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetSocialOAuthRes {
    private String jwtToken;
    private Long memberId;
    private String homeId;
    private String name;
    private String memberImg;
    private String accessToken;
    private String tokenType;


}
