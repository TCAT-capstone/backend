package return_a.tcat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import return_a.tcat.domain.Category;
import return_a.tcat.domain.TicketValidation;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TicketCreateReqDto {
    private Long member_id;
    private Long book_id;
    private String ticket_img;
    private TicketValidation ticketValidation; //VERIFIED, UNVERIFIED

    //ticket정보
    private String ticket_title;
    private String ticket_date;
    private String ticket_seat;
    private String ticket_location;

    //글 정보
    private String title;
    private String content;
    private LocalDateTime date;
    private Category category;

}
