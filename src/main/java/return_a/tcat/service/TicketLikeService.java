package return_a.tcat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.Member;
import return_a.tcat.domain.Ticket;
import return_a.tcat.domain.TicketLike;
import return_a.tcat.repository.MemberRepository;
import return_a.tcat.repository.TicketLikeRepository;
import return_a.tcat.repository.TicketRepository;

@Service
@Transactional(readOnly = false) //ticket의 like수 조정하기 때문에 readOnly하면 오류남
@RequiredArgsConstructor
public class TicketLikeService {

    private final TicketLikeRepository ticketLikeRepository;
    private final TicketRepository ticketRepository;
    private final MemberRepository memberRepository;

    /**
     *좋아요 누르기
     */
    public boolean likes(Long memberId, Long ticketId){

        Member member=memberRepository.findOne(memberId); //like를 누른 회원
        Ticket ticket=ticketRepository.findOne(ticketId); //like당하는 티켓
        Long hostmemberId=ticket.getMember().getId(); //그 티켓의 주인인 회원
        Member hostmember=memberRepository.findOne(hostmemberId);

        if(isNotAlreadyLike(member,ticket)){
            ticketLikeRepository.save(new TicketLike(member,ticket));
            ticket.addLikeCount();
            hostmember.addLikeCount();
            return true;
        }

        else{
            ticketLikeRepository.deleteLike(member,ticket);
            ticket.subtractLikeCount();
            hostmember.subtractLikeCount();
            return false;
        }


    }

    private boolean isNotAlreadyLike(Member member, Ticket ticket){
        return ticketLikeRepository.findByMemberAndTicket(member,ticket).isEmpty();
    }


}
