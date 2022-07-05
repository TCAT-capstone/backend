package return_a.tcat.dto.ticket;

import lombok.Getter;

import java.util.List;

@Getter
public class TicketListResDto {

    private List<TicketDto> tickets;

    public TicketListResDto(List<TicketDto> tickets) {
        this.tickets = tickets;
    }
}
