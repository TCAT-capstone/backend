package return_a.tcat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.*;
import return_a.tcat.dto.ticket.TicketReqDto;
import return_a.tcat.repository.MemberRepository;
import return_a.tcat.repository.TicketRepository;
import return_a.tcat.repository.TicketbookRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final MemberRepository memberRepository;
    private final TicketbookRepository ticketbookRepository;

    /**
     * 티켓 저장
     */
    @Transactional
    public Long save(TicketReqDto ticketDto) {

        Member member = memberRepository.findOne(ticketDto.getMemberId());
        Ticketbook ticketbook = ticketbookRepository.findOne(ticketDto.getTicketbookId());

        Ticket ticket = Ticket.builder()
                .member(member)
                .ticketbook(ticketbook)
                .ticketImg(ticketDto.getTicketImg())
                .ticketValidation(ticketDto.getTicketValidation())
                .likeCount(0)
                .ticketTitle(ticketDto.getTicketTitle())
                .ticketDate(ticketDto.getTicketDate())
                .ticketSeat(ticketDto.getTicketSeat())
                .ticketLocation(ticketDto.getTicketLocation())
                .casting(ticketDto.getCasting())
                .title(ticketDto.getTitle())
                .content(ticketDto.getContent())
                .date(LocalDateTime.now())
                .category(ticketDto.getCategory())
                .build();

        ticketRepository.save(ticket);
        member.addTicketCount();
        return ticket.getId();
    }

    public Ticket findTicket(Long ticketId) {
        return ticketRepository.findOne(ticketId);
    }

    /**
     * 전체 티켓 조회
     */
    public List<Ticket> findTickets() {
        return ticketRepository.findAll();
    }

    /**
     * 유저의 티켓 조회
     */
    public List<Ticket> findMemberTickets(Long memberId) {
        return ticketRepository.findByMemberId(memberId);
    }

    /**
     * 티켓북의 티켓 조회
     */
    public List<Ticket> findTicketsByTicketbook(Long ticketbookId) {
        return ticketRepository.findByTicketbookId(ticketbookId);
    }

    /**
     * 티켓 삭제
     */
    @Transactional
    public void deleteById(Long ticketId) {
        Ticket ticket=ticketRepository.findOne(ticketId);
        Member member = ticket.getMember();
        Integer likecount=ticket.getLikeCount();
        member.subtractTotalLikeCount(likecount);
        member.subtractTicketCount();
        ticketRepository.deleteById(ticketId);
    }

    /**
     * 티켓 수정
     */
    @Transactional
    public Ticket updateTicket(Long ticketId,TicketReqDto ticketReqDto){
        Ticket ticket=ticketRepository.findOne(ticketId);
        ticket.changeTicket(ticketReqDto);
        return ticket;
    }

    /**
     * 통합 검색
     */
    public List<Ticket> findByKeyword(String keyword,
                                      String ticketTitle, LocalDateTime ticketDate,
                                      String ticketSeat,String ticketLocation){
        return ticketRepository.findByKeyword(keyword,ticketTitle,ticketDate,ticketSeat,ticketLocation);
    }


}
