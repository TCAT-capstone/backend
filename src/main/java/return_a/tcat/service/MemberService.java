package return_a.tcat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.Member;
import return_a.tcat.dto.member.MemberReqDto;
import return_a.tcat.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    private void validateDuplicateMember(String homeId) {
        List<Member> findMembers =
                memberRepository.findByHomeIds(homeId);
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

    @Transactional
    public String updateMemberName(Long memberId,MemberReqDto memberReqDto){
        Member member=memberRepository.findOne(memberId);
        member.changeMemberName(memberReqDto.getName());
        return member.getName();
    }

    @Transactional
    public String updateMemberBio(Long memberId,MemberReqDto memberReqDto){
        Member member=memberRepository.findOne(memberId);
        member.changeMemberBio(memberReqDto.getBio());
        return member.getBio();
    }

    @Transactional
    public String updateMemberHomeId(Long memberId,MemberReqDto memberReqDto){
        Member member=memberRepository.findOne(memberId);
        validateDuplicateMember(memberReqDto.getHomeId());
        member.changeMemberHomeId(memberReqDto.getHomeId());
        return member.getHomeId();
    }

    @Transactional
    public String updateMemberImg(Long memberId,MemberReqDto memberReqDto){
        Member member=memberRepository.findOne(memberId);
        member.changeMemberImg(memberReqDto.getMemberImg());
        return member.getMemberImg();
    }



}
