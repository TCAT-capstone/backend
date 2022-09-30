package return_a.tcat.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import return_a.tcat.domain.Member;

@Getter
@RequiredArgsConstructor
public class MemberSignUpResDto {

    private String name;
    private String homeId;

    @Builder
    public MemberSignUpResDto(Member member) {
        this.name = member.getName();
        this.homeId = member.getHomeId();
    }

}
