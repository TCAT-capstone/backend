package return_a.tcat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import return_a.tcat.domain.Category;
import return_a.tcat.domain.Ticket;
import return_a.tcat.domain.TicketValidation;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TicketDetailResDto {

    private String name;
    private String ticket_img;
    private TicketValidation ticketValidation;
    private Long like_count;

    //글 정보
    private String title;
    private String content;
    private LocalDateTime date;

    private Category category;

    @Builder
    public TicketDetailResDto(Ticket ticket){
        this.name=ticket.getMember().getName();
        this.ticket_img=ticket.getTicket_img();
        this.ticketValidation=ticket.getTicketValidation();
        this.like_count=ticket.getLike_count();

        this.title=ticket.getTitle();
        this.content=ticket.getContent();
        this.date=ticket.getDate();

        this.category=ticket.getCategory();
    }
}
