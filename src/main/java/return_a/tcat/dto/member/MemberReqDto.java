package return_a.tcat.dto.member;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberReqDto {

    private String homeId;
    private String name;
    private String bio;
    private String memberImg;
    private String accessToken;
    private String provider;
    private Integer likeCount;
    private Integer ticketCount;
}

