package return_a.tcat.dto.ticketbook;

import lombok.Getter;
import lombok.NoArgsConstructor;
import return_a.tcat.domain.Ticketbook;

@Getter
@NoArgsConstructor
public class TicketbookDto {

    private Long id;
    private String name;
    private String ticketbookImg;
    private String description;

    public TicketbookDto(Ticketbook ticketbook) {
        id = ticketbook.getId();
        name = ticketbook.getName();
        ticketbookImg = ticketbook.getTicketbookImg();
        description = ticketbook.getDescription();
    }
}
