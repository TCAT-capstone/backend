package return_a.tcat.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberSignUpReqDto {

    private String name;
    private String homeId;

    @Builder
    public MemberSignUpReqDto(String name, String homeId) {
        this.name = name;
        this.homeId = homeId;
    }
}
