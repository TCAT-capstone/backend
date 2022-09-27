package return_a.tcat.service;

import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.*;
import return_a.tcat.dto.ticket.TicketListResDto;
import return_a.tcat.dto.ticket.TicketReqDto;
import return_a.tcat.dto.ticket.TicketSimpleDto;
import return_a.tcat.repository.MemberRepository;
import return_a.tcat.repository.TicketRepository;
import return_a.tcat.repository.TicketbookRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    public Long save(TicketReqDto ticketDto, Long memberId) {

        Member member = memberRepository.findOne(memberId);
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
    public TicketListResDto findTickets(Integer cursorLikeCount, Long cursorId, Pageable pageable) {

        Page<Ticket> findAllTickets = ticketRepository.findAll(cursorLikeCount, cursorId, pageable);
        List<Ticket> findTickets = findAllTickets.getContent();
        List<TicketSimpleDto> collect = findTickets.stream()
                .map(t -> new TicketSimpleDto(t))
                .collect(Collectors.toList());
        TicketListResDto response = new TicketListResDto(collect);
        if (findAllTickets.getTotalElements() == 0) {
            response.setHasNotTicket(true);
        }


        return response;
    }

    public TicketListResDto findFollowingTickets(Long memberId, LocalDateTime cursorDate, Long cursorId, Pageable pageable) {

        Page<Ticket> findFollowingTickets=ticketRepository.findFollowingTickets(memberId,cursorDate,cursorId,pageable);
        List<Ticket> findTickets = findFollowingTickets.getContent();
        List<TicketSimpleDto> collect = findTickets.stream()
                .map(t -> new TicketSimpleDto(t))
                .collect(Collectors.toList());
        TicketListResDto response = new TicketListResDto(collect);
        if (findFollowingTickets.getTotalElements() == 0) {
            response.setHasNotTicket(true);
        }

        return response;
    }

    /**
     * 유저의 티켓 조회
     */
    public TicketListResDto findMemberTickets(Long cursorId, Long memberId, Pageable pageable) {
        Page<Ticket> findAllTickets = ticketRepository.findByMemberId(cursorId, memberId, pageable);
        List<Ticket> findTickets = findAllTickets.getContent();
        List<TicketSimpleDto> collect = findTickets.stream()
                .map(t -> new TicketSimpleDto(t))
                .collect(Collectors.toList());
        TicketListResDto response = new TicketListResDto(collect);
        if (findAllTickets.getTotalElements() == 0) {
            response.setHasNotTicket(true);
        }

        return response;
    }

    /**
     * 티켓북의 티켓 조회
     */
    public TicketListResDto findTicketsByTicketbook(Long cursorId, Long ticketbookId, Pageable pageable) {
        Page<Ticket> findTicketsByTicketbook = ticketRepository.findByTicketbookId(cursorId, ticketbookId, pageable);

        List<Ticket> findTickets = findTicketsByTicketbook.getContent();
        List<TicketSimpleDto> collect = findTickets.stream()
                .map(t -> new TicketSimpleDto(t))
                .collect(Collectors.toList());

        TicketListResDto response = new TicketListResDto(collect);

        if (findTicketsByTicketbook.getTotalElements() == 0) {
            response.setHasNotTicket(true);
        }


        return response;
    }

    /**
     * 티켓 삭제
     */
    @Transactional
    public void deleteById(Long ticketId) {
        Ticket ticket = ticketRepository.findOne(ticketId);
        Member member = ticket.getMember();
        Integer likecount = ticket.getLikeCount();
        member.subtractTotalLikeCount(likecount);
        member.subtractTicketCount();
        ticketRepository.deleteById(ticketId);
    }

    public Boolean checkTicketOwner(Long memberId, Long ticketId) {
        Ticket ticket = ticketRepository.findOne(ticketId);
        Member member = ticket.getMember();
        if (memberId.equals(member.getId())) {
            return true;
        }

        return false;

    }

    /**
     * 티켓 수정
     */
    @Transactional
    public Ticket updateTicket(Long ticketId, TicketReqDto ticketReqDto) {
        Ticket ticket = ticketRepository.findOne(ticketId);
        Ticketbook ticketbook = null;

        if (ticketReqDto.getTicketbookId() != null) {
            ticketbook = ticketbookRepository.findOne(ticketReqDto.getTicketbookId());
        }

        ticket.changeTicket(ticketReqDto, ticketbook);

        return ticket;
    }

    /**
     * 통합 검색
     */
    public TicketListResDto findByKeyword(Long cursorId,
                                          String keyword,
                                          String ticketTitle, String ticketDate,
                                          String ticketSeat, String ticketLocation,
                                          Pageable pageable) {


        Page<Ticket> findTicketsByKeyword = ticketRepository.findByKeyword(cursorId, keyword, ticketTitle, ticketDate, ticketSeat, ticketLocation, pageable);
        List<Ticket> findTickets = findTicketsByKeyword.getContent();
        List<TicketSimpleDto> collect = findTickets.stream()
                .map(t -> new TicketSimpleDto(t))
                .collect(Collectors.toList());

        TicketListResDto response = new TicketListResDto(collect);

        if (findTicketsByKeyword.getTotalElements() == 0) {
            response.setHasNotTicket(true);
        }

        return response;
    }


}
