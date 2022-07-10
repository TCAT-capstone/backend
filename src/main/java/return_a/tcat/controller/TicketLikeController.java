package return_a.tcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import return_a.tcat.dto.ticketLike.TicketLikeReqDto;
import return_a.tcat.service.TicketLikeService;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketLikeController {

    private final TicketLikeService ticketLikeService;

    @PostMapping("/tickets/{ticketId}/like")
    public ResponseEntity<Object> updateLike(@PathVariable("ticketId") Long ticketId, @RequestBody TicketLikeReqDto ticketLikeReqDto){
        Long memberId=ticketLikeReqDto.getMemberId();
        boolean result=ticketLikeService.likes(memberId,ticketId);
        return ResponseEntity.ok().body(result);
    }
}
