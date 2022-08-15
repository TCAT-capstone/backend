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
    private Long ticketbookId;

    @Builder
    public MemberSignUpResDto(Member member) {
        this.name = member.getName();
        this.homeId = member.getHomeId();
    }

    @Builder
    public void setTicketbookId(Long ticketbookId){
        this.ticketbookId=ticketbookId;
    }
}
