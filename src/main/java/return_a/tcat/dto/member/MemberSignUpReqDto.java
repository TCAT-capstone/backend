package return_a.tcat.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberSignUpReqDto {

    private String name;
    private String homeId;
    private String bio;

    @Builder
    public MemberSignUpReqDto(String name, String homeId, String bio){
        this.name=name;
        this.homeId=homeId;
        this.bio=bio;
    }
}
