package return_a.tcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import return_a.tcat.domain.Ticket;
import return_a.tcat.dto.ticket.TicketListResDto;
import return_a.tcat.dto.ticket.TicketDto;
import return_a.tcat.dto.ticket.TicketReqDto;
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
    public ResponseEntity<TicketListResDto> getTrendTickets() {
        List<Ticket> findTickets = ticketService.findTickets();
        List<TicketDto> collect = findTickets.stream()
                .map(t -> new TicketDto(t))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new TicketListResDto(collect));
    }

    @GetMapping("/ticketbooks/{ticketbookId}/tickets")
    public ResponseEntity<TicketListResDto> getTicketbookTickets(@PathVariable("ticketbookId") Long ticketbookId) {
        List<Ticket> findTickets = ticketService.findTicketsByTicketbook(ticketbookId);
        List<TicketDto> collect = findTickets.stream()
                .map(t -> new TicketDto(t))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new TicketListResDto(collect));
    }

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<TicketDto> getTicketDetail(@PathVariable("ticketId") Long ticketId) {
        Ticket ticket = ticketService.findTicket(ticketId);
        return ResponseEntity.status(HttpStatus.OK).body(new TicketDto(ticket));
    }


    @PostMapping("/tickets")
    public ResponseEntity<TicketDto> saveTicket(@RequestBody @Valid TicketReqDto ticketReqDto) {
        Long ticketId = ticketService.save(ticketReqDto);
        Ticket ticket = ticketService.findTicket(ticketId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TicketDto(ticket));
    }


    @DeleteMapping("/tickets/{ticketId}")
    public ResponseEntity<Object> deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteById(ticketId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/tickets/{ticketId}")
    public ResponseEntity<TicketDto> updateTicket( @RequestBody TicketReqDto ticketReqDto,@PathVariable("ticketId") Long ticketId){
        Ticket ticket=ticketService.updateTicket(ticketId,ticketReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(new TicketDto(ticket));
    }

}
