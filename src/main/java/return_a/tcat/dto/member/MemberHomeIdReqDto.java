package return_a.tcat.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberHomeIdReqDto {

    private String homeId;

    @Builder
    public MemberHomeIdReqDto(String homeId) {
        this.homeId = homeId;
    }
}
