package return_a.tcat.dto.ticketbook;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class TicketbookReqDto {

    private Long id;
    private String name;
    private String ticketbookImg;
}
