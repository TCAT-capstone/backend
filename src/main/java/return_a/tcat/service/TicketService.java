package return_a.tcat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.*;
import return_a.tcat.dto.TicketCreateReqDto;
import return_a.tcat.dto.TicketCreateResDto;
import return_a.tcat.repository.MemberRepository;
import return_a.tcat.repository.TicketRepository;
import return_a.tcat.repository.TicketbookRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final MemberRepository memberRepository;
    private final TicketbookRepository ticketbookRepository;

    //상세 글 작성
    @Transactional
    public Long save(Ticket ticket){

        ticketRepository.save(ticket);
        return ticket.getId();
    }

    public Ticket findTicket(Long ticket_id) {
        return ticketRepository.findOne(ticket_id);
    }

    //전체 티켓 조회
    public List<Ticket> findTickets(){
        return ticketRepository.findAll();
    }

    //유저의 티켓 조회
    public List<Ticket> findMemberTickets(Long memberId){
        return ticketRepository.findByMember_Id(memberId);
    }

    //티켓북의 티켓 조회
    public List<Ticket> findByTicketbooks(Long book_id){
        return ticketRepository.findByBook_Id(book_id);
    }
    //티켓삭제
    @Transactional
    public void deleteById(Long ticket_id) {
        ticketRepository.deleteById(ticket_id);
    }

    public Ticket createTicket(TicketCreateReqDto request){
        Ticket ticket=new Ticket();
        Member member= memberRepository.findOne(request.getMember_id());
        Ticketbook ticketbook=ticketbookRepository.findOne(request.getBook_id());
        ticket.setMember(member);
        ticket.setTicketbook(ticketbook);
        ticket.setTicket_img(request.getTicket_img());
        ticket.setTicketValidation(request.getTicketValidation());

        ticket.setTicket_title(request.getTicket_title());
        ticket.setTicket_date(request.getTicket_date());
        ticket.setTicket_seat(request.getTicket_seat());
        ticket.setTicket_location(request.getTicket_location());

        ticket.setTitle(request.getTitle());
        ticket.setContent(request.getContent());
        ticket.setDate(LocalDateTime.now());
        ticket.setCategory(request.getCategory());

        return ticket;
    }
}
