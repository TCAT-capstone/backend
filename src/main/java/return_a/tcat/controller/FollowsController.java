package return_a.tcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import return_a.tcat.domain.Member;
import return_a.tcat.dto.follows.FollowsListResDto;
import return_a.tcat.service.FollowsService;
import return_a.tcat.service.MemberService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowsController {

    private final FollowsService followsService;
    private final MemberService memberService;

    @GetMapping("/member/{homeId}/following")
    public ResponseEntity<FollowsListResDto> getFollowing(@PathVariable(value="homeId") String homeId){
        return ResponseEntity.status(HttpStatus.OK).body(followsService.getFollowing(homeId));
    }

    @GetMapping("/member/{homeId}/follower")
    public ResponseEntity<FollowsListResDto> getFollower(@PathVariable(value="homeId") String homeId){
        return ResponseEntity.status(HttpStatus.OK).body(followsService.getFollower(homeId));
    }

    //중복 domain저장되는거 수정
    @PutMapping("/member/{homeId}/following")
    public ResponseEntity<FollowsListResDto> following(@PathVariable(value="homeId") String homeId){
        Member member = memberService.findMemberByAuth();
        Long memberId = member.getId();
        followsService.save(memberId,homeId);
        return ResponseEntity.status(HttpStatus.OK).body(followsService.getFollowing(member.getHomeId()));
    }

}
