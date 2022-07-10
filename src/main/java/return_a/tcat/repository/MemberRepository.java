package return_a.tcat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import return_a.tcat.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class,id);
    }

    public List<Member> findByHomeIds(String homeId) {
        return em.createQuery("select m from Member m where m.homeId = :homeId", Member.class)
                .setParameter("homeId", homeId)
                .getResultList();
    }

    public Member findByHomeId(String homeId) {
        return em.createQuery("select m from Member m where m.homeId = :homeId", Member.class)
                .setParameter("homeId", homeId)
                .getSingleResult();
    }

    public void deleteById(Long memberId){
        Member member=em.find(Member.class,memberId);
        em.remove(member);
    }

}
