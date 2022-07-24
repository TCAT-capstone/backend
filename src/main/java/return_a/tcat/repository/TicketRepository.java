package return_a.tcat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import return_a.tcat.domain.Ticket;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketRepository {

    private final EntityManager em;

    public void save(Ticket ticket) {
        em.persist(ticket);
    }

    public Ticket findOne(Long id) {
        return em.find(Ticket.class, id);
    }

    public List<Ticket> findAll() {
        return em.createQuery("select t from Ticket t", Ticket.class)
                .getResultList();
    }

    public List<Ticket> findByTicketbookId(Long ticketbookId) {
        return em.createQuery("select t from Ticket t where t.ticketbook.id= :ticketbookId", Ticket.class)
                .setParameter("ticketbookId", ticketbookId)
                .getResultList();
    }

    public List<Ticket> findByMemberId(Long memberId) {
        return em.createQuery("select t from Ticket t where t.member.id= :memberId", Ticket.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public void deleteById(Long ticketId) {
        Ticket ticket = em.find(Ticket.class, ticketId);
        em.remove(ticket);
    }



}
