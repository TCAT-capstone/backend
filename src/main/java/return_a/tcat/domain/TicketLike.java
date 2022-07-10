package return_a.tcat.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ticket_like_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ticket_id")
    private Ticket ticket;

    public TicketLike(Member member,Ticket ticket){
        this.member=member;
        this.ticket=ticket;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getLikes().add(this);
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        ticket.getLikes().add(this);
    }
}
