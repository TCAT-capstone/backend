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
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(length = 30)
    private String name;

    @Column(length = 100)
    private String bio;

    private String memberImg;
    private String accessToken;
    private String provider;
    private Integer likeCount;
    private Integer ticketCount;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Ticketbook> ticketbooks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<TicketLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @Builder
    public Member(String name, String bio, String memberImg, String accessToken, String provider,
                  Integer likeCount, Integer ticketCount) {
        this.name = name;
        this.bio = bio;
        this.memberImg = memberImg;
        this.accessToken = accessToken;
        this.provider = provider;
        this.likeCount = likeCount;
        this.ticketCount = ticketCount;
    }
}
