package return_a.tcat.dto.ticket;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import return_a.tcat.domain.Category;
import return_a.tcat.domain.TicketValidation;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TicketReqDto {

    private Long memberId;
    private Long ticketbookId;

    private String ticketImg;
    private TicketValidation ticketValidation;

    private String ticketTitle;
    private LocalDate ticketDate;
    private String ticketSeat;
    private String ticketLocation;

    private String casting;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private LocalDateTime date;
    private Category category;
}
