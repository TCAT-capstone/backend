package return_a.tcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import return_a.tcat.domain.Member;
import return_a.tcat.dto.member.*;
import return_a.tcat.exception.DuplicateMemberException;
import return_a.tcat.service.MemberService;
import return_a.tcat.service.TicketbookService;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TicketbookService ticketbookService;

    @GetMapping("/members/my-profile")
    public ResponseEntity<MemberProfileResDto> myProfile() {
        Member member = memberService.findMemberByAuth();
        return ResponseEntity.status(HttpStatus.OK).body(new MemberProfileResDto(member));
    }

    @PatchMapping("members/my-profile")
    public ResponseEntity<MemberEditResDto> updateMyProfile(@RequestBody MemberEditReqDto memberEditReqDto) {
        Member member = memberService.findMemberByAuth();
        Long memberId = member.getId();
        memberService.updateMemberProfile(memberId, memberEditReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(new MemberEditResDto(member));
    }

    @GetMapping("/members/{homeId}/profile")
    public MemberProfileResDto profile(@PathVariable("homeId") String homeId) {
        Member member = memberService.findMemberByHomeId(homeId);
        return new MemberProfileResDto(member);
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Object> deleteMember(@PathVariable("memberId") Long memberId) {
        memberService.deleteById(memberId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("members/signup")
    public ResponseEntity<MemberSignUpResDto> signup(@RequestBody MemberSignUpReqDto memberSignUpReqDto) {
        Member member = memberService.findMemberByAuth();
        Long memberId = member.getId();
        memberService.updateMemberInfo(memberId, memberSignUpReqDto);
        MemberSignUpResDto memberSignUpResDto = new MemberSignUpResDto(member);
        memberSignUpResDto.setTicketbookId(ticketbookService.saveDefault(memberId,"default"));

        return ResponseEntity.status(HttpStatus.OK).body(memberSignUpResDto);
    }

    @PostMapping("members/homeId/duplicate")
    public ResponseEntity<Object> checkHomeId(@RequestBody MemberHomeIdReqDto memberHomeIdReqDto) {
        if (!memberService.checkDuplicateHomeId(memberHomeIdReqDto.getHomeId())) {
            throw new DuplicateMemberException();
        }
        return ResponseEntity.ok().build();
    }
}
