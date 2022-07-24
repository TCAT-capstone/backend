package return_a.tcat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.Ticketbook;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketbookRepository {

    private final EntityManager em;

    public void save(Ticketbook ticketbook){
        em.persist(ticketbook);
    }
    public Ticketbook findOne(Long id) {
        return em.find(Ticketbook.class, id);
    }

    //member의 티켓북 꺼내오기
    public List<Ticketbook> findByMemberId(Long memberId) {
        return em.createQuery("select tb from Ticketbook tb where tb.member.id= :memberId",Ticketbook.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public void deleteById(Long id){
        Ticketbook ticketbook=em.find(Ticketbook.class,id);
        em.remove(ticketbook);
    }
}
