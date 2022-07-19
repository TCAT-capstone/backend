package return_a.tcat.oauth;

import org.springframework.http.ResponseEntity;

public interface SocialOauth {

    String getOauthRedirectURL();
    ResponseEntity<String> requestAccessToken(String code);

    default OAuthConstant.SocialLoginType type() {
        if (this instanceof GoogleOauth) {
            return OAuthConstant.SocialLoginType.GOOGLE;
        } else {
            return null;
        }
    }
}
