package return_a.tcat.dto.follows;

import lombok.Getter;
import return_a.tcat.domain.Member;

@Getter
public class FollowsDto {

    private final String targetHomeId;
    private final String name;
    private final String memberImg;
    private final String bio;

    public FollowsDto(Member member) {
        this.targetHomeId = member.getHomeId();
        this.name = member.getName();
        this.memberImg = member.getMemberImg();
        this.bio = member.getBio();

    }

}
