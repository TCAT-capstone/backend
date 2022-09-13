package return_a.tcat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.Follows;
import return_a.tcat.domain.Member;
import return_a.tcat.dto.follows.FollowsDto;
import return_a.tcat.dto.follows.FollowsListResDto;
import return_a.tcat.repository.FollowsRepository;
import return_a.tcat.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowsService {

    private final FollowsRepository followsRepository;
    private final MemberRepository memberRepository;

    public FollowsListResDto getFollowing(String homeId){
        Member member = memberRepository.findByHomeId(homeId).get();
        List<Follows> findFollows= followsRepository.findFollowing(member.getId());
        List<FollowsDto> collect = findFollows.stream()
                .map(f -> new FollowsDto(memberRepository.findByHomeId(f.getHomeId()).get()))
                .collect(Collectors.toList());
        return new FollowsListResDto(collect);
    }

    public FollowsListResDto getFollower(String homeId){
        List<Follows> findFollows = followsRepository.findFollower(homeId);
        List<FollowsDto> collect = findFollows.stream()
                .map(f -> new FollowsDto(memberRepository.findOne(f.getMember().getId())))
                .collect(Collectors.toList());
        return new FollowsListResDto(collect);
    }

    @Transactional
    public void save(Long memberId, String homeId){
        Member member = memberRepository.findOne(memberId);
        Follows follows = Follows.builder()
                        .member(member)
                        .homeId(homeId)
                        .build();

        followsRepository.save(follows);
    }
}
