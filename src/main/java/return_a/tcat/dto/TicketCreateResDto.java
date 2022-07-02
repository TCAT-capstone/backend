package return_a.tcat.dto;

import lombok.Getter;

@Getter
public class TicketCreateResDto {
    private Long id;

    public TicketCreateResDto(Long id) {
        this.id = id;
    }
}
