package return_a.tcat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import return_a.tcat.domain.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        return em.createQuery("select t from Ticket t order by t.likeCount desc", Ticket.class)
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

    public List<Ticket> findByKeyword(String keyword,
                                      String ticketTitle, LocalDateTime ticketDate,
                                      String ticketSeat,String ticketLocation){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Ticket> cq=cb.createQuery(Ticket.class);
        Root<Ticket> t=cq.from(Ticket.class);

        List<Predicate> criteria=new ArrayList<>();

        if(ticketTitle!=null&&ticketTitle!=""){
            Predicate ticketTitleInfo=cb.equal(t.get("ticketTitle"),ticketTitle);
            criteria.add(ticketTitleInfo);
        }
        if(ticketDate!=null){
            Predicate ticketDateInfo=cb.equal(t.get("ticketDate"),ticketDate);
            criteria.add(ticketDateInfo);
        }
        if(ticketSeat!=null&&ticketSeat!=""){
            Predicate ticketSeatInfo=cb.equal(t.get("ticketSeat"),ticketSeat);
            criteria.add(ticketSeatInfo);
        }
        if(ticketLocation!=null&&ticketLocation!=""){
            Predicate ticketLocationInfo=cb.equal(t.get("ticketLocation"),ticketLocation);
            criteria.add(ticketLocationInfo);
        }

        if (StringUtils.hasText(keyword)) {
            Predicate keywordInfo =
                    cb.like(t.<String>get("title"), "%" +
                            keyword+ "%");

            keywordInfo=cb.or(keywordInfo,cb.like(t.<String>get("content"), "%" +
                    keyword+ "%"));

            criteria.add(keywordInfo);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Ticket> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }

}
