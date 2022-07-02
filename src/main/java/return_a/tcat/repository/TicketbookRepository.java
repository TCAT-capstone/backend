package return_a.tcat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import return_a.tcat.domain.Ticketbook;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class TicketbookRepository {

    @PersistenceContext
    private EntityManager em;

    public Ticketbook findOne(Long id){
        return em.find(Ticketbook.class,id);
    }
}
