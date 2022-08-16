package return_a.tcat.dto.ticket;

import lombok.Getter;

import java.util.List;

@Getter
public class TicketListResDto {

    private List<TicketSimpleDto> tickets;
    private Boolean hasNotTicket;

    public void setHasNotTicket(Boolean hasNotTicket) {
        this.hasNotTicket = hasNotTicket;
    }

    public TicketListResDto(List<TicketSimpleDto> tickets) {
        this.tickets = tickets;
    }
}
