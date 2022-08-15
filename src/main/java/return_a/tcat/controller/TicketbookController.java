package return_a.tcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import return_a.tcat.domain.Member;
import return_a.tcat.domain.Ticketbook;
import return_a.tcat.dto.ticketbook.TicketbookListResDto;
import return_a.tcat.dto.ticketbook.TicketbookDto;
import return_a.tcat.dto.ticketbook.TicketbookReqDto;
import return_a.tcat.service.MemberService;
import return_a.tcat.service.TicketbookService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketbookController {

    private final TicketbookService ticketbookService;
    private final MemberService memberService;

    @GetMapping("/members/{homeId}/ticketbooks")
    public ResponseEntity<TicketbookListResDto> findTicketbooks(@PathVariable("homeId") String homeId){
        List<Ticketbook> findTicketbooks = ticketbookService.findTicketbooks(homeId);
        //엔티티 -> DTO 변환
        List<TicketbookDto> collect = findTicketbooks.stream()
                .map(t -> new TicketbookDto(t))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new TicketbookListResDto(collect));
    }

    @PostMapping("/ticketbooks")
    public ResponseEntity<TicketbookDto> saveTicketbook(@RequestBody @Valid TicketbookReqDto ticketbookReqDto){
        Member member = memberService.findMemberByAuth();
        Long memberId = member.getId();
        Long ticketbookId=ticketbookService.save(ticketbookReqDto, memberId);
        Ticketbook ticketbook=ticketbookService.findTicketbook(ticketbookId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new TicketbookDto(ticketbook));
    }

    @DeleteMapping("/ticketbooks/{ticketbookId}")
    public ResponseEntity<Object> deleteTicketbook(@PathVariable("ticketbookId") Long ticketbookId){
        ticketbookService.deleteById(ticketbookId);
        return ResponseEntity.ok().build();
    }

}
