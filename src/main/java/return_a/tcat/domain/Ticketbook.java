package return_a.tcat.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Ticketbook {
    @Id
    @GeneratedValue
    @Column(name="ticketbook_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private String name;

    @OneToMany(mappedBy="ticketbook", cascade = CascadeType.ALL)
    private List<Ticket> tickets=new ArrayList<>();
}
