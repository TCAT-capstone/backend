package return_a.tcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import return_a.tcat.domain.Member;
import return_a.tcat.dto.like.TicketLikeResDto;
import return_a.tcat.service.MemberService;
import return_a.tcat.service.TicketLikeService;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketLikeController {

    private final TicketLikeService ticketLikeService;
    private final MemberService memberService;

    @PostMapping("/tickets/{ticketId}/like")
    public ResponseEntity<TicketLikeResDto> updateLike(@PathVariable("ticketId") Long ticketId){
        Member member = memberService.findMemberByAuth();
        Long memberId = member.getId();

        return ResponseEntity.ok().body(ticketLikeService.likes(memberId,ticketId));
    }
}
