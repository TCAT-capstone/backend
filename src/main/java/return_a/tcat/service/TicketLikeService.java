package return_a.tcat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.Member;
import return_a.tcat.domain.Ticket;
import return_a.tcat.domain.TicketLike;
import return_a.tcat.dto.like.TicketLikeResDto;
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
    public TicketLikeResDto likes(Long memberId, Long ticketId){

        Member member=memberRepository.findOne(memberId);
        Ticket ticket=ticketRepository.findOne(ticketId);
        Long hostmemberId=ticket.getMember().getId();
        Member hostmember=memberRepository.findOne(hostmemberId);

        TicketLikeResDto ticketLikeResDto;

        if(isNotAlreadyLike(member,ticket)){
            ticketLikeRepository.save(new TicketLike(member,ticket));
            ticket.addLikeCount();
            hostmember.addLikeCount();
            ticketLikeResDto = new TicketLikeResDto(true,ticket.getLikeCount());
        }

        else{
            ticketLikeRepository.deleteLike(member,ticket);
            ticket.subtractLikeCount();
            hostmember.subtractLikeCount();
            ticketLikeResDto = new TicketLikeResDto(false,ticket.getLikeCount());
        }

        return ticketLikeResDto;
    }

    public TicketLikeResDto getLike(Long memberId, Long ticketId){
        Member member=memberRepository.findOne(memberId);
        Ticket ticket=ticketRepository.findOne(ticketId);

        TicketLikeResDto ticketLikeResDto;

        if(isNotAlreadyLike(member,ticket)){
            ticketLikeResDto = new TicketLikeResDto(false,ticket.getLikeCount());
        }
        else{
            ticketLikeResDto = new TicketLikeResDto(true,ticket.getLikeCount());
        }

        return ticketLikeResDto;
    }

    private boolean isNotAlreadyLike(Member member, Ticket ticket){
        return ticketLikeRepository.findByMemberAndTicket(member,ticket).isEmpty();
    }


}
