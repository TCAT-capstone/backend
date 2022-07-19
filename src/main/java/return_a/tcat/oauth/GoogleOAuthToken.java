package return_a.tcat.oauth;


import lombok.Getter;


@Getter
public class GoogleOAuthToken {
    private String access_token;
    private int expires_in;
    private String scope;
    private String token_type;
    private String id_token;

    public GoogleOAuthToken(){};

    public GoogleOAuthToken(String access_token,int expires_in,String scope,String token_type,String id_token){
        this.access_token=access_token;
        this.expires_in=expires_in;
        this.scope=scope;
        this.token_type=token_type;
        this.id_token=id_token;
    }


}
