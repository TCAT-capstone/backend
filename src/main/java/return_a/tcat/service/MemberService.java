package return_a.tcat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.Member;
import return_a.tcat.dto.member.MemberCreateDto;
import return_a.tcat.exception.ResourceNotFoundException;
import return_a.tcat.repository.MemberRepository;
import return_a.tcat.security.UserPrincipal;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberCreateDto memberDto) {
        Member member = Member.builder()
                .homeId(null)
                .bio(null)
                .name(memberDto.getName())
                .memberImg(memberDto.getMemberImg())
                .email(memberDto.getEmail())
                .provider(memberDto.getProvider())
                .likeCount(0)
                .ticketCount(0)
                .build();

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers =
                memberRepository.findByHomeIds(member.getHomeId());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Member findMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public Member findMemberByHomeId(String homeId) {
        return memberRepository.findByHomeId(homeId);
    }

    @Transactional
    public void deleteById(Long memberId) {
        memberRepository.deleteById(memberId);
    }

//    @Transactional
//    public String updateMemberName(Long memberId,MemberReqDto memberReqDto){
//        Member member=memberRepository.findOne(memberId);
//        member.changeMemberName(memberReqDto.getName());
//        return member.getName();
//    }
//
//    @Transactional
//    public String updateMemberBio(Long memberId,MemberReqDto memberReqDto){
//        Member member=memberRepository.findOne(memberId);
//        member.changeMemberBio(memberReqDto.getBio());
//        return member.getBio();
//    }

    public UserDetails loadUserById(String id) {
        Member member = memberRepository.findOne(Long.parseLong(id));
        if (member == null)
            throw new ResourceNotFoundException("User", "id", id);
        return UserPrincipal.create(member);
    }


}
