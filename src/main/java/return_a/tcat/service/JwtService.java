package return_a.tcat.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import return_a.tcat.oauth.Secret;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class JwtService {

    public String createJwt(Long memberId){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("memberId",memberId)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365))) //발급날짜 계산
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY) //signature 부분
                .compact();
    }

    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }


    public int getMemberId(){
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new IllegalStateException("accessToken이 비어있습니다.");
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new IllegalStateException("잘못된 JWT입니다.");
        }

        // 3. memberId 추출
        return claims.getBody().get("memberId",Integer.class);
    }

}
