package return_a.tcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import return_a.tcat.domain.Ticketbook;
import return_a.tcat.dto.ticketbook.TicketbookListResDto;
import return_a.tcat.dto.ticketbook.TicketbookDto;
import return_a.tcat.dto.ticketbook.TicketbookReqDto;
import return_a.tcat.service.TicketbookService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketbookController {

    private final TicketbookService ticketbookService;

    @GetMapping("/members/{memberId}/ticketbooks")
    public ResponseEntity<TicketbookListResDto> findTicketbooks(@PathVariable("memberId") Long memberId){
        List<Ticketbook> findTicketbooks = ticketbookService.findTicketbooks(memberId);
        //엔티티 -> DTO 변환
        List<TicketbookDto> collect = findTicketbooks.stream()
                .map(t -> new TicketbookDto(t))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new TicketbookListResDto(collect));
    }

    @PostMapping("/ticketbooks")
    public ResponseEntity<TicketbookDto> saveTicketbook(@RequestBody @Valid TicketbookReqDto ticketbookReqDto){
        Long ticketbookId=ticketbookService.save(ticketbookReqDto);
        Ticketbook ticketbook=ticketbookService.findTicketbook(ticketbookId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TicketbookDto(ticketbook));
    }

    @DeleteMapping("/ticketbooks/{ticketbookId}")
    public ResponseEntity<Object> deleteTicketbook(@PathVariable("ticketbookId") Long ticketbookId){
        ticketbookService.deleteById(ticketbookId);
        return ResponseEntity.ok().build();
    }

}
