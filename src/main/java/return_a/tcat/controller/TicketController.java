package return_a.tcat.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import return_a.tcat.domain.*;
import return_a.tcat.dto.TicketCreateReqDto;
import return_a.tcat.dto.TicketCreateResDto;
import return_a.tcat.dto.TicketDetailResDto;
import return_a.tcat.dto.TicketListResDto;
import return_a.tcat.service.TicketService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/tickets/trending")
    public List<TicketListResDto> all(){

        List<Ticket> findTickets = ticketService.findTickets();
        //엔티티 -> DTO 변환
        List<TicketListResDto> collect = findTickets.stream()
                .map(t -> new TicketListResDto(t))
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/ticketbooks/{ticketbook_id}/tickets")
    public List<TicketListResDto> ticketbookAll(@PathVariable("ticketbook_id") Long book_id) {
        List<Ticket> findTickets = ticketService.findByTicketbooks(book_id);
        //엔티티 -> DTO 변환
        List<TicketListResDto> collect = findTickets.stream()
                .map(t -> new TicketListResDto(t))
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/tickets/{ticket_id}")
    public TicketDetailResDto detail(@PathVariable("ticket_id") Long ticket_id){

        Ticket findTicket = ticketService.findTicket(ticket_id);
        return new TicketDetailResDto(findTicket);
    }


    @PostMapping("/tickets")
    public TicketCreateResDto saveTicket(@RequestBody @Valid TicketCreateReqDto request) {
        Ticket ticket=ticketService.createTicket(request);
        Long id = ticketService.save(ticket);
        return new TicketCreateResDto(id);
    }


    @DeleteMapping("/tickets/{ticket_id}")
    public void deleteTicket(@PathVariable Long ticket_id){
        ticketService.deleteById(ticket_id);
    }



}
