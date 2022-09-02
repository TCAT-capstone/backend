package return_a.tcat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import return_a.tcat.domain.Template;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TemplateRepository {

    private final EntityManager em;

    public List<Template> findAll() {
        return em.createQuery("select t from Template t", Template.class)
                .getResultList();
    }
}
