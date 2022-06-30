package return_a.tcat.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    @Column(length=30)
    private String name;

    @Column(length=100)
    private String bio;

    private String member_img;
    private String access_token;
    private String provider;
    private Long like_count;
    private Long ticket_count;

    @OneToMany(mappedBy="member", cascade = CascadeType.ALL)
    private List<Ticketbook> ticketbooks=new ArrayList<>();

    @OneToMany(mappedBy="member", cascade = CascadeType.ALL)
    private List<TicketLike> likes=new ArrayList<>();

}
