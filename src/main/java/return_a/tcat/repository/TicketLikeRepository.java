package return_a.tcat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import return_a.tcat.domain.Member;
import return_a.tcat.domain.Ticket;
import return_a.tcat.domain.TicketLike;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketLikeRepository {

    private final EntityManager em;

    public void save(TicketLike ticketLike){
        em.persist(ticketLike);
    }

    public List<TicketLike> findByMemberAndTicket(Member member, Ticket ticket){
        Long memberId= member.getId();
        Long ticketId= ticket.getId();
        return em.createQuery("select l from TicketLike l where l.member.id=:memberId and l.ticket.id=:ticketId",TicketLike.class)
                .setParameter("memberId",memberId)
                .setParameter("ticketId",ticketId)
                .getResultList();
    }

    public TicketLike findTicketLike(Member member, Ticket ticket){
        Long memberId= member.getId();
        Long ticketId= ticket.getId();
        return em.createQuery("select l from TicketLike l where l.member.id=:memberId and l.ticket.id=:ticketId",TicketLike.class)
                .setParameter("memberId",memberId)
                .setParameter("ticketId",ticketId)
                .getSingleResult();
    }

    public void deleteLike(Member member, Ticket ticket){
       TicketLike ticketLike=findTicketLike(member,ticket);
        em.remove(ticketLike);
    }
}
