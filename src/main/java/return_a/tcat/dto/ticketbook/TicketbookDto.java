package return_a.tcat.dto.ticketbook;

import lombok.Getter;
import return_a.tcat.domain.Ticketbook;

@Getter
public class TicketbookDto {

    private final Long id;
    private final String name;

    public TicketbookDto(Ticketbook ticketbook){
        id=ticketbook.getId();
        name= ticketbook.getName();
    }
}
