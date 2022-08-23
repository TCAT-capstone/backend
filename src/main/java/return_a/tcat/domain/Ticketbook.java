package return_a.tcat.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import return_a.tcat.dto.ticketbook.TicketbookDto;
import return_a.tcat.dto.ticketbook.TicketbookReqDto;

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
    private String description;

    @OneToMany(mappedBy = "ticketbook", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
        member.getTicketbooks().add(this);
    }

    @Builder
    public Ticketbook(String name, String ticketbookImg, String description) {
        this.name = name;
        this.ticketbookImg = ticketbookImg;
        this.description = description;
    }

    public void changeTicketbook(TicketbookDto ticketbookDto) {

        if (ticketbookDto.getName() != null) {
            this.name = ticketbookDto.getName();
        }

        if (ticketbookDto.getTicketbookImg() != null) {
            this.ticketbookImg = ticketbookDto.getTicketbookImg();
        }

        if(ticketbookDto.getDescription() != null) {
            this.description = ticketbookDto.getDescription();
        }


    }


}
