package return_a.tcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import return_a.tcat.domain.Ticket;
import return_a.tcat.dto.ticket.TicketListResDto;
import return_a.tcat.dto.ticket.TicketDto;
import return_a.tcat.dto.ticket.TicketReqDto;
import return_a.tcat.dto.ticket.TicketSimpleDto;
import return_a.tcat.service.TicketService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/tickets/trending")
    public ResponseEntity<TicketListResDto> getTrendTickets(@RequestParam(value="cursorLikeCount",required = false) Integer cursorLikeCount,
                                                            @RequestParam(value="cursorId",required = false) Long cursorId,
                                                            @PageableDefault(size = 10) Pageable pageable) {
        TicketListResDto ticketListResDto = ticketService.findTickets(cursorLikeCount, cursorId,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ticketListResDto);
    }

    @GetMapping("/search")
    public ResponseEntity<TicketListResDto> getSearchTickets(@RequestParam(value="cursorId",required = false) Long cursorId,
                                                             @RequestParam(value="keyword") String keyword,
                                                             @RequestParam(value="ticketTitle", required = false) String ticketTitle,
                                                             @RequestParam(value="ticketDate", required = false)
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ticketDate,
                                                             @RequestParam(value="ticketSeat", required = false) String ticketSeat,
                                                             @RequestParam(value="ticketLocation", required = false) String ticketLocation,
                                                             @PageableDefault(size = 10) Pageable pageable){
        TicketListResDto ticketListResDto = ticketService.findByKeyword(cursorId,keyword,ticketTitle,ticketDate,ticketSeat,ticketLocation,pageable);

        return ResponseEntity.status(HttpStatus.OK).body(ticketListResDto);
    }

    @GetMapping("/ticketbooks/{ticketbookId}/tickets")
    public ResponseEntity<TicketListResDto> getTicketbookTickets(@RequestParam(value="cursorId",required = false) Long cursorId,
                                                                 @PathVariable("ticketbookId") Long ticketbookId,
                                                                 @PageableDefault(size = 10) Pageable pageable) {
        TicketListResDto ticketListResDto = ticketService.findTicketsByTicketbook(cursorId, ticketbookId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ticketListResDto);
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
