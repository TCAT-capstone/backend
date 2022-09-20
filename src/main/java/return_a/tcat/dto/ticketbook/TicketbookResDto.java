package return_a.tcat.dto.ticketbook;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Getter
public class TicketbookResDto {

    private List<TicketbookDto> ticketbooks;
    private String sequence;

    public TicketbookResDto(List<TicketbookDto> ticketbooks, String sequence) {
        this.ticketbooks = ticketbooks;
        this.sequence = sequence;
    }

}
