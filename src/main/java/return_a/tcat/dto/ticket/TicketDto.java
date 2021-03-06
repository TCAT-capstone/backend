package return_a.tcat.dto.ticket;

import lombok.Getter;
import return_a.tcat.domain.Category;
import return_a.tcat.domain.Ticket;
import return_a.tcat.domain.TicketValidation;

import java.time.LocalDateTime;

@Getter
public class TicketDto {

    private final Long ticketId;
    private final Long ticketbookId;

    private final Long memberId;
    private final String memberHomeId;
    private final String memberImg;
    private final String memberName;
    private final String memberBio;

    private final String ticketImg;
    private final TicketValidation ticketValidation;
    private final Integer likeCount;

    private final String ticketTitle;
    private final LocalDateTime ticketDate;
    private final String ticketSeat;
    private final String ticketLocation;

    private final String title;
    private final String content;
    private final LocalDateTime date;
    private final String casting;
    private final Category category;

    public TicketDto(Ticket ticket) {
        ticketId = ticket.getId();
        ticketbookId = ticket.getTicketbook().getId();
        memberId = ticket.getMember().getId();
        memberHomeId = ticket.getMember().getHomeId();
        memberImg = ticket.getMember().getMemberImg();
        memberName = ticket.getMember().getName();
        memberBio = ticket.getMember().getBio();
        ticketImg = ticket.getTicketImg();
        ticketValidation = ticket.getTicketValidation();
        likeCount = ticket.getLikeCount();
        ticketTitle = ticket.getTicketTitle();
        ticketDate = ticket.getTicketDate();
        ticketSeat = ticket.getTicketSeat();
        ticketLocation = ticket.getTicketLocation();
        title = ticket.getTitle();
        content = ticket.getContent();
        date = ticket.getDate();
        casting = ticket.getCasting();
        category = ticket.getCategory();
    }
}
