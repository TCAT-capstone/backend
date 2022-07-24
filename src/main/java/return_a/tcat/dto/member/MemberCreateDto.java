package return_a.tcat.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import return_a.tcat.domain.AuthProvider;

@Getter
@NoArgsConstructor
public class MemberCreateDto {

    private String name;
    private String memberImg;
    private String email;
    private AuthProvider provider;

    @Builder
    public MemberCreateDto(String name, String memberImg, String email, AuthProvider provider) {
        this.name = name;
        this.memberImg = memberImg;
        this.email = email;
        this.provider = provider;
    }
}

