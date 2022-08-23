package return_a.tcat.dto.ticketbook;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class TicketbookReqDto {

    private String sequence;
    private List<TicketbookDto> append;
    private List<TicketbookDto> update;
    private List<TicketbookDto> delete;

}
