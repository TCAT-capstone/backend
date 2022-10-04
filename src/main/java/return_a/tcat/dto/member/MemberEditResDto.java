package return_a.tcat.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import return_a.tcat.domain.Member;

@Getter
@AllArgsConstructor
public class MemberEditResDto {

    private String name;
    private String bio;
    private String memeberImg;

    @Builder
    public MemberEditResDto(Member member){
        this.name=member.getName();
        this.bio=member.getBio();
        this.bio=member.getMemberImg();
    }
}
