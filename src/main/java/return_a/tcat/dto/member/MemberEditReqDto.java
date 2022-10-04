package return_a.tcat.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberEditReqDto {

    private String name;
    private String bio;
    private String memberImg;

}
