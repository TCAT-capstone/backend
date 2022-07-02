package return_a.tcat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import return_a.tcat.domain.Ticket;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class TicketListResDto {
    private Long ticket_id;
    private String ticket_img;
    private String title;
    private Long like_count;
    private String name;
    private LocalDateTime date;

    @Builder
    public TicketListResDto(Ticket ticket){
        this.ticket_id=ticket.getId();
        this.ticket_img=ticket.getTicket_img();
        this.title=ticket.getTitle();
        this.like_count=ticket.getLike_count();
        this.name=ticket.getMember().getName();
        this.date=ticket.getDate();
    }

}
