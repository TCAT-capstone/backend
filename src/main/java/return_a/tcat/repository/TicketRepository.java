package return_a.tcat.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import return_a.tcat.domain.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

import static return_a.tcat.domain.QTicket.*;
import static return_a.tcat.domain.QFollows.*;

@Repository
@RequiredArgsConstructor
public class TicketRepository {

    private final EntityManager em;
    JPAQueryFactory queryFactory;

    public Integer findCountByTicketBook(Long ticketbookId) {
        List<Ticket> count = em.createQuery("select t from Ticket t where t.ticketbook.id= :ticketbookId", Ticket.class)
                .setParameter("ticketbookId", ticketbookId)
                .getResultList();

        return Integer.valueOf(count.size());
    }

    public Integer findLikeByTicketBook(Long ticketbookId) {
        Query query = em.createQuery("select COALESCE(sum(t.likeCount),0) from Ticket t where t.ticketbook.id= :ticketbookId");
        String result = String.valueOf(query.setParameter("ticketbookId", ticketbookId).getSingleResult());
        Integer count=Integer.parseInt(String.valueOf(query.setParameter("ticketbookId", ticketbookId).getSingleResult()));

        return count;
    }


    public void save(Ticket ticket) {
        em.persist(ticket);
    }

    public Page<Ticket> findFollowingTickets(Long memberId, LocalDateTime cursorDate, Long cursorId, Pageable pageable) {
        queryFactory = new JPAQueryFactory(em);
        List<Ticket> findFollowingTickets = queryFactory.selectFrom(ticket)
                .join(follows)
                .on(ticket.member.homeId.eq(follows.targetHomeId))
                .where(memberMatch(memberId), cursorDateAndCursorId(cursorDate, cursorId))
                .orderBy(ticket.date.desc())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(findFollowingTickets, pageable, findFollowingTickets::size);
    }


    private BooleanExpression memberMatch(Long memberId) {
        if (memberId.equals(null)) {
            return null;
        }
        return follows.member.id.eq(memberId);
    }

    private BooleanExpression cursorDateAndCursorId(LocalDateTime date, Long cursorId) {
        if (date == null || cursorId == null) {
            return null;
        }

        return ticket.date.eq(date)
                .and(ticket.id.gt(cursorId))
                .or(ticket.date.lt(date));
    }

    public Ticket findOne(Long id) {
        return em.find(Ticket.class, id);
    }

    public Page<Ticket> findAll(Integer cursorLikeCount, Long cursorId, Pageable pageable) {
        queryFactory = new JPAQueryFactory(em);
        List<Ticket> findAllTickets = queryFactory.selectFrom(ticket)
                .where(currentdate(LocalDateTime.now()), cursorLikeCountAndCursorId(cursorLikeCount, cursorId))
                .orderBy(ticket.likeCount.desc(), ticket.id.asc())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(findAllTickets, pageable, findAllTickets::size);
    }

    private BooleanExpression currentdate(LocalDateTime currentdate) {
        return ticket.date.between(currentdate.minusDays(7), currentdate);
    }

    private BooleanExpression cursorLikeCountAndCursorId(Integer cursorLikeCount, Long cursorId) {
        if (cursorLikeCount == null || cursorId == null) {
            return null;
        }

        return ticket.likeCount.eq(cursorLikeCount)
                .and(ticket.id.gt(cursorId))
                .or(ticket.likeCount.lt(cursorLikeCount));
    }

    public Page<Ticket> findByTicketbookId(Long cursorId, Long ticketbookId, Pageable pageable) {
        queryFactory = new JPAQueryFactory(em);
        List<Ticket> findAllTickets = queryFactory.selectFrom(ticket)
                .where(ticket.ticketbook.id.eq(ticketbookId).and(cursorId(cursorId)))
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(findAllTickets, pageable, findAllTickets::size);
    }

    private BooleanExpression cursorId(Long cursorId) {
        return cursorId == null ? null : ticket.id.gt(cursorId);
    }

    public Page<Ticket> findByMemberId(Long cursorId, Long memberId, Pageable pageable) {
        queryFactory = new JPAQueryFactory(em);
        List<Ticket> findAllTickets = queryFactory.selectFrom(ticket)
                .where(ticket.member.id.eq(memberId).and(cursorId(cursorId)))
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(findAllTickets, pageable, findAllTickets::size);
    }

    public void deleteById(Long ticketId) {
        Ticket ticket = em.find(Ticket.class, ticketId);
        em.remove(ticket);
    }

    public Page<Ticket> findByKeyword(Long cursorId, String keyword,
                                      String ticketTitle, String ticketDate,
                                      String ticketSeat, String ticketLocation,
                                      Pageable pageable) {
        queryFactory = new JPAQueryFactory(em);

        List<Ticket> findTicketByKeyword = queryFactory.selectFrom(ticket)
                .where(ticketTitle(ticketTitle), ticketDate(ticketDate), ticketSeat(ticketSeat), ticketLocation(ticketLocation), keywordLike(keyword), cursorId(cursorId))
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(findTicketByKeyword, pageable, findTicketByKeyword::size);
    }

    private BooleanExpression ticketTitle(String ticketTitle) {
        if (ticketTitle == null || ticketTitle == "") {
            return null;
        }
        return ticket.ticketTitle.like("%" + ticketTitle + "%");
    }

    private BooleanExpression ticketDate(String ticketDate) {
        if (ticketDate == null || ticketDate == "") {
            return null;
        }

        DateTemplate date = Expressions.dateTemplate(LocalDateTime.class, "DATE_FORMAT({0}, {1})", ticket.ticketDate, "%Y-%m-%d");
        return date.eq(ticketDate);
    }

    private BooleanExpression ticketSeat(String ticketSeat) {
        if (ticketSeat == null || ticketSeat == "") {
            return null;
        }
        return ticket.ticketSeat.like("%" + ticketSeat + "%");
    }

    private BooleanExpression ticketLocation(String ticketLocation) {
        if (ticketLocation == null || ticketLocation == "") {
            return null;
        }
        return ticket.ticketLocation.like("%" + ticketLocation + "%");
    }

    private BooleanExpression keywordLike(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null;
        }
        return ticket.title.like("%" + keyword + "%").or(ticket.content.like("%" + keyword + "%"));
    }

}
