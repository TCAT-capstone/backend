package return_a.tcat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.Ticket;
import return_a.tcat.repository.TicketRepository;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

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

    //티켓삭제
    public void deleteById(Long ticket_id) {
        ticketRepository.deleteById(ticket_id);
    }
}
