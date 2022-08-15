package return_a.tcat.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticketbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketbook_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;

    private String ticketbookImg;

    @OneToMany(mappedBy = "ticketbook", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
        member.getTicketbooks().add(this);
    }

    @Builder
    public Ticketbook(String name, String ticketbookImg) {
        this.name = name;
        this.ticketbookImg = ticketbookImg;
    }

}
