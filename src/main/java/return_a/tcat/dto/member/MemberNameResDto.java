package return_a.tcat.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import return_a.tcat.domain.Member;

@Getter
@AllArgsConstructor
public class MemberNameResDto {
    private Long id;
    private String homeId;
    private String name;

    @Builder
    public MemberNameResDto(Member member){
        this.id=member.getId();
        this.homeId=member.getHomeId();
        this.name=member.getName();
    }
}
