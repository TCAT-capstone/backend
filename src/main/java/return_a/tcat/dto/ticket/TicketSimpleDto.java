package return_a.tcat.dto.ticket;

import lombok.Getter;
import return_a.tcat.domain.Ticket;

import java.time.LocalDateTime;

@Getter
public class TicketSimpleDto {

    private final Long ticketId;
    private final String homeId;
    private final String memberName;

    private final String ticketImg;
    private Integer likeCount;

    private final String title;
    private final LocalDateTime date;

    public TicketSimpleDto(Ticket ticket) {
        ticketId = ticket.getId();
        homeId = ticket.getMember().getHomeId();
        memberName = ticket.getMember().getName();
        ticketImg = ticket.getTicketImg();
        likeCount = ticket.getLikeCount();
        title = ticket.getTitle();
        date = ticket.getDate();
    }
}
