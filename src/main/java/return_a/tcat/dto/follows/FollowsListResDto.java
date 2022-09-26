package return_a.tcat.dto.follows;

import lombok.Getter;

import java.util.List;

@Getter
public class FollowsListResDto {

    private List<FollowsDto> follows;

    public FollowsListResDto(List<FollowsDto> follows) {
        this.follows = follows;
    }


}
