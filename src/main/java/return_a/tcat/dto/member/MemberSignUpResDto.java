package return_a.tcat.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import return_a.tcat.domain.Member;

@Getter
@AllArgsConstructor
public class MemberSignUpResDto {

    private String name;
    private String homeId;
    private String bio;

    @Builder
    public MemberSignUpResDto(Member member){
        this.name=member.getName();
        this.homeId=member.getHomeId();
        this.bio= member.getBio();
    }
}
