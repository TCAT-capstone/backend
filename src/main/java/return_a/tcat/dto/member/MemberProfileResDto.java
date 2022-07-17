package return_a.tcat.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import return_a.tcat.domain.Member;

@Getter
@AllArgsConstructor
public class MemberProfileResDto {

    private Long id;
    private String homeId;
    private String name;
    private String bio;
    private String memberImg;
    private String email;
    private String provider;
    private Integer likeCount;
    private Integer ticketCount;

    public MemberProfileResDto(Member member){
        this.id=member.getId();
        this.homeId=member.getHomeId();
        this.name=member.getName();
        this.bio=member.getBio();
        this.memberImg=member.getMemberImg();
        this.email=member.getEmail();
        this.provider=member.getProvider();
        this.likeCount=member.getLikeCount();
        this.ticketCount=member.getTicketCount();
    }
}
