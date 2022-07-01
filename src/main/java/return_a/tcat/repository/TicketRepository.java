package return_a.tcat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import return_a.tcat.domain.Member;
import return_a.tcat.domain.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketRepository{

    @PersistenceContext
    private EntityManager em;

    public void save(Ticket ticket){
        em.persist(ticket);
    }

    public Ticket findOne(Long id){
        return em.find(Ticket.class,id);
    }

    public List<Ticket> findAll(){
        return em.createQuery("select t from Ticket t", Ticket.class)
                .getResultList();
    }

    public List<Ticket> findByMember_Id(Long member_id){
        return em.createQuery("select t from Ticket t where t.member.id= :member_id",Ticket.class)
                .setParameter("member_id",member_id)
                .getResultList();
    }

    public void deleteById(Long ticket_id){
        em.createQuery("delete from Ticket t where t.id =:ticket_id",Ticket.class)
                .setParameter("ticket_id", ticket_id)
                .executeUpdate();
    }

}
