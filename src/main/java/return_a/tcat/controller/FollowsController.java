package return_a.tcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import return_a.tcat.domain.Member;
import return_a.tcat.dto.follows.FollowResDto;
import return_a.tcat.dto.follows.FollowsListResDto;
import return_a.tcat.service.FollowsService;
import return_a.tcat.service.MemberService;

import javax.validation.Valid;

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

    //중복 domain저장되는거 수정->수정하기
    @PutMapping("/member/{homeId}/following")
    public ResponseEntity<FollowsListResDto> following(@PathVariable(value="homeId") String homeId,
                                                       @RequestBody @Valid FollowResDto followResDto){
        Member member = memberService.findMemberByAuth();
        Long memberId = member.getId();
        followsService.save(memberId,followResDto.getTargetHomeId());
        FollowsListResDto followsListResDto = followsService.getFollowing(member.getHomeId());
        return ResponseEntity.status(HttpStatus.OK).body(followsListResDto);
    }

    @DeleteMapping("/member/{homeId}/following/{targetHomeId}")
    public ResponseEntity<Object> unfollow(@PathVariable(value="homeId") String homeId,
                                           @PathVariable(value="targetHomeId") String targetHomeId){
        Member member = memberService.findMemberByAuth();
        Long memberId = member.getId();
        followsService.delete(memberId,targetHomeId);
        return ResponseEntity.ok().build();
    }

}
