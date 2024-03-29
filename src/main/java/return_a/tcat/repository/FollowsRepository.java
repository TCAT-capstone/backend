package return_a.tcat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import return_a.tcat.domain.Follows;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowsRepository {

    private final EntityManager em;

    public void save(Follows follows) {
        em.persist(follows);
    }

    public List<Follows> findFollows(Long memberId, String targetHomeId) {
        return em.createQuery("select f from Follows f where f.member.id = :memberId and f.targetHomeId = :targetHomeId", Follows.class)
                .setParameter("memberId", memberId)
                .setParameter("targetHomeId", targetHomeId)
                .getResultList();
    }

    public List<Follows> findFollowing(Long memberId) {
        return em.createQuery("select f from Follows f where f.member.id = :memberId", Follows.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<Follows> findFollower(String homeId) {
        return em.createQuery("select f from Follows f where f.targetHomeId = :homeId", Follows.class)
                .setParameter("homeId", homeId)
                .getResultList();
    }

    public void delete(Long memberId, String targetHomeId) {
        Follows follows = em.createQuery("select f from Follows f where f.member.id = :memberId and f.targetHomeId = :targetHomeId", Follows.class)
                .setParameter("memberId", memberId)
                .setParameter("targetHomeId", targetHomeId)
                .getSingleResult();
        em.remove(follows);
    }


}
