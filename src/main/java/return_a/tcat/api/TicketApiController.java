package return_a.tcat.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import return_a.tcat.domain.*;
import return_a.tcat.service.TicketService;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketApiController {
    private final TicketService ticketService;

    @GetMapping("/tickets/trending")
    public Result all(){

        List<Ticket> findTickets = ticketService.findTickets();
        //엔티티 -> DTO 변환
        List<TicketDto> collect = findTickets.stream()
                .map(t -> new TicketDto(t.getId(),t.getTicket_img(),t.getTitle(),t.getLike_count(),t.getMember().getName(),t.getDate()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class TicketDto {
        private Long ticket_id;
        private String ticket_img;
        private String title;
        private Long like_count;
        private String name;
        private LocalDateTime date;
    }

    @GetMapping("/tickets/{ticket_id}")
    public detailTicketResponse detail(@PathVariable("ticket_id") Long ticket_id){

        Ticket findTicket = ticketService.findTicket(ticket_id);
        return new detailTicketResponse(
                findTicket.getMember().getName(), findTicket.getTicket_img(), findTicket.getStatus(),findTicket.getLike_count(),
                findTicket.getTitle(), findTicket.getContent(), findTicket.getDate(),findTicket.getCategory());
    }

    @Data
    @AllArgsConstructor
    static class detailTicketResponse {
        private String name;
        private String ticket_img;
        private TicketStatus status; //YES,NO
        private Long like_count;

        //글 정보
        private String title;
        private String content;
        private LocalDateTime date;

        private Category category;
    }
/* set안쓰고 수정찾기
    @PostMapping("/tickets")
    public CreateTicketResponse saveMemberV2(@RequestBody @Valid CreateTicketRequest request) {
        Ticket ticket= new Ticket();
        ticket.s
        Long id = ticketService.save(ticket);
        return new CreateTicketResponse(id);
    }
*/
    @Data
    static class CreateTicketRequest{
        private Long member_id;
        private Long book_id;
        private String ticket_img;
        private TicketStatus status; //YES,NO

        //ticket정보
        private String ticket_title;
        private String ticket_date;
        private String ticket_seat;
        private String ticket_location;

        //글 정보
        private String title;
        private String content;

        private Category category;

    }

    @Data
    static class CreateTicketResponse {
        private Long id;

        public CreateTicketResponse(Long id) {
            this.id = id;
        }
    }

    @DeleteMapping("/tickets/{ticket_id}")
    public void deleteTicket(@PathVariable Long ticket_id){
        ticketService.deleteById(ticket_id);
    }
}
