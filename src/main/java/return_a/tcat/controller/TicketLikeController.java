package return_a.tcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import return_a.tcat.domain.Member;
import return_a.tcat.domain.Ticket;
import return_a.tcat.dto.like.TicketLikeResDto;
import return_a.tcat.service.MemberService;
import return_a.tcat.service.TicketLikeService;
import return_a.tcat.service.TicketService;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketLikeController {

    private final TicketLikeService ticketLikeService;
    private final MemberService memberService;
    private final TicketService ticketService;

    @GetMapping("/tickets/{ticketId}/like")
    public ResponseEntity<TicketLikeResDto> getLike(@PathVariable("ticketId") Long ticketId) {
        if (memberService.checkMemberByAuth()) {
            Member member = memberService.findMemberByAuth();
            Long memberId = member.getId();
            return ResponseEntity.ok().body(ticketLikeService.getLike(memberId, ticketId));
        } else {
            TicketLikeResDto ticketLikeResDto;
            Ticket ticket = ticketService.findTicket(ticketId);
            ticketLikeResDto = new TicketLikeResDto(false, ticket.getLikeCount());
            return ResponseEntity.ok().body(ticketLikeResDto);
        }
    }


        @PostMapping("/tickets/{ticketId}/like")
    public ResponseEntity<TicketLikeResDto> updateLike(@PathVariable("ticketId") Long ticketId) {
        Member member = memberService.findMemberByAuth();
        Long memberId = member.getId();

        return ResponseEntity.ok().body(ticketLikeService.likes(memberId, ticketId));
    }
}
