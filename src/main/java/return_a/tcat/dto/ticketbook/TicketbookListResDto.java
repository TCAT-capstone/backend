package return_a.tcat.dto.ticketbook;

import lombok.Getter;

import java.util.List;

@Getter
public class TicketbookListResDto {

    private List<TicketbookDto> ticketbooks;

    public TicketbookListResDto(List<TicketbookDto> ticketbooks) {
        this.ticketbooks = ticketbooks;
    }
}
