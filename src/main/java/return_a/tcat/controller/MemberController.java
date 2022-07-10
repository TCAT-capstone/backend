package return_a.tcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import return_a.tcat.domain.Member;
import return_a.tcat.dto.member.MemberDto;
import return_a.tcat.dto.member.MemberNameResDto;
import return_a.tcat.dto.member.MemberProfileResDto;
import return_a.tcat.dto.member.MemberReqDto;
import return_a.tcat.service.MemberService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/{memberId}/profile")
    public ResponseEntity<MemberProfileResDto> profile(@PathVariable("memberId") Long memberId){
        Member member= memberService.findMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(new MemberProfileResDto(member));
    }

//예비용 사용안할수도있음
//    @GetMapping("/members/{homeId}/profile")
//    public MemberProfileResDto profile(@PathVariable("homeId") String homeId){
//        Member member= memberService.findMemberByHomeId(homeId);
//        return new MemberProfileResDto(member);
//    }

    @GetMapping("/members/{memberId}/name")
    public ResponseEntity<MemberNameResDto> name(@PathVariable("memberId") Long memberId){
        Member member= memberService.findMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(new MemberNameResDto(member));
    }

    @PostMapping("/members")
    public ResponseEntity<MemberDto> saveMember(@RequestBody @Valid MemberReqDto memberReqDto){
        Long memberId=memberService.save(memberReqDto);
        Member member=memberService.findMember(memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MemberDto(member));
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Object> deleteMember(@PathVariable("memberId") Long memberId){
        memberService.deleteById(memberId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("members/{memberId}/name")
    public ResponseEntity<String> updateMemberName(@RequestBody MemberReqDto memberReqDto, @PathVariable("memberId") Long memberId){

        String name=memberService.updateMemberName(memberId,memberReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(name);
    }

    @PatchMapping("members/{memberId}/bio")
    public ResponseEntity<String> updateMemberBio(@RequestBody MemberReqDto memberReqDto, @PathVariable("memberId") Long memberId){

        String bio=memberService.updateMemberBio(memberId,memberReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(bio);
    }

}
