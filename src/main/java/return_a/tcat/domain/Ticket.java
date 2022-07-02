package return_a.tcat.domain;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Ticket {

    @Id
    @GeneratedValue
    @Column(name="ticket_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ticketbook_id")
    private Ticketbook ticketbook;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private String ticket_img;

    @Enumerated(EnumType.STRING)
    private TicketValidation ticketValidation; //YES,NO
    private Long like_count;

    //ticket정보
    private String ticket_title;
    private String ticket_date;
    private String ticket_seat;
    private String ticket_location;

    //글 정보
    private String casting;
    private String title;
    private String content;
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy="ticket", cascade = CascadeType.ALL)
    private List<TicketLike> likes=new ArrayList<>();





}
