package return_a.tcat.dto.member;

import lombok.Getter;
import return_a.tcat.domain.Member;


@Getter
public class MemberDto {

    private final Long memberId;
    private final String homeId;
    private final String name;
    private final String bio;
    private final String memberImg;
    private final String email;
    private final String provider;
    private final Integer likeCount;
    private final Integer ticketCount;

    public MemberDto(Member member){
        memberId=member.getId();
        homeId=member.getHomeId();
        name=member.getName();
        bio=member.getBio();
        memberImg=member.getMemberImg();
        email=member.getEmail();
        provider=member.getProvider();
        likeCount=member.getLikeCount();
        ticketCount=member.getTicketCount();
    }
}
