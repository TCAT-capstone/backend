package return_a.tcat.domain;

import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket {

    @Id
    @GeneratedValue
    @Column(name = "ticket_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticketbook_id")
    private Ticketbook ticketbook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String ticketImg;

    @Enumerated(EnumType.STRING)
    private TicketValidation ticketValidation; //YES,NO
    private Integer likeCount;

    //ticket정보
    private String ticketTitle;
    private LocalDate ticketDate;
    private String ticketSeat;
    private String ticketLocation;

    //글 정보
    private String casting;
    private String title;
    private String content;
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<TicketLike> likes = new ArrayList<>();

    @Builder
    public Ticket(Ticketbook ticketbook, Member member, String ticketImg, TicketValidation ticketValidation,
                  Integer likeCount, String ticketTitle, LocalDate ticketDate, String ticketSeat, String ticketLocation,
                  String casting, String title, String content, LocalDateTime date, Category category) {
        this.ticketbook = ticketbook;
        this.member = member;
        this.ticketImg = ticketImg;
        this.ticketValidation = ticketValidation;
        this.likeCount = likeCount;
        this.ticketTitle = ticketTitle;
        this.ticketDate = ticketDate;
        this.ticketSeat = ticketSeat;
        this.ticketLocation = ticketLocation;
        this.casting = casting;
        this.title = title;
        this.content = content;
        this.date = date;
        this.category = category;
    }


    // 연관관계 메서드
    public void changeTicketbook(Ticketbook ticketbook) {
        if(this.ticketbook != null){
            this.ticketbook.getTickets().remove(this);
        }

        this.ticketbook = ticketbook;
        ticketbook.getTickets().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getTickets().add(this);
    }

    /**
     * 좋아요 수 증가
     */
    public void addLikeCount() {
        this.likeCount++;
    }

    /**
     * 좋아요 수 감소
     */
    public void subtractLikeCount() {
        this.likeCount--;
    }

}
