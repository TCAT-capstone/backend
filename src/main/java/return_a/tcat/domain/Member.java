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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 16)
    private String homeId;

    @Column(length = 30)
    private String name;

    @Column(length = 100)
    private String bio;

    private String memberImg;
    private String email;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private Integer likeCount;
    private Integer ticketCount;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Ticketbook> ticketbooks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<TicketLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @Builder
    public Member(String homeId, String name, String bio, String memberImg, String email, AuthProvider provider,
                  Integer likeCount, Integer ticketCount) {
        this.homeId = homeId;
        this.name = name;
        this.bio = bio;
        this.memberImg = memberImg;
        this.email = email;
        this.provider = provider;
        this.likeCount = likeCount;
        this.ticketCount = ticketCount;
    }

    public void changeMemberName(String name) {
        this.name = name;
    }

    public void changeMemberBio(String bio) {
        this.bio = bio;
    }

    /**
     * 좋아요 받은수 증가
     */
    public void addLikeCount() {
        this.likeCount++;
    }

    /**
     * 좋아요 받은수 감소
     */
    public void subtractLikeCount() {
        this.likeCount--;
    }
}
