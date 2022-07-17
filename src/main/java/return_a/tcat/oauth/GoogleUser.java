package return_a.tcat.oauth;

import lombok.Getter;


@Getter
public class GoogleUser {
    public String id;
    public String email;
    public boolean verified_email;
    public String picture;

    public GoogleUser(){};

    public GoogleUser(String id, String email, boolean verified_email,String picture){
        this.id=id;
        this.email=email;
        this.verified_email=verified_email;
        this.picture=picture;
    }
}
