package return_a.tcat.dto.like;

import lombok.Getter;

@Getter
public class TicketLikeResDto {

    private Boolean status;
    private Integer count;

    public TicketLikeResDto(Boolean status, Integer count){
        this.status = status;
        this.count = count;
    }
}
