package return_a.tcat.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    private String sequence;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Ticketbook> ticketbooks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<TicketLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Follows> follows = new ArrayList<>();

    @Builder
    public Member(String homeId, String name, String bio, String memberImg, String email, AuthProvider provider,
                  Integer likeCount, Integer ticketCount, String sequence) {
        this.homeId = homeId;
        this.name = name;
        this.bio = bio;
        this.memberImg = memberImg;
        this.email = email;
        this.provider = provider;
        this.likeCount = likeCount;
        this.ticketCount = ticketCount;
        this.sequence = sequence;
    }

    public void changeMemberInfo(String name, String homeId) {
        this.name = name;
        this.homeId = homeId;
    }

    public void changeMemberProfile(String name, String bio) {
        this.name = name;
        this.bio = bio;

    }

    public void changeTicketbookSequence(String sequence) {
        if (sequence != null) {
            this.sequence = sequence;
        }
    }

    public void replaceTicketbookSequence(Long tempId, Long ticketbookId) {

        this.sequence = sequence.replace(tempId.toString(),ticketbookId.toString());

    }

    public void removeTicketbookSequence(Long ticketbookId) {
        String[] sequenceArray = sequence.split(",");
        List<String> sequenceList = new ArrayList<>(Arrays.asList(sequenceArray));
        sequenceList.remove(ticketbookId.toString());
        this.sequence = StringUtils.join(sequenceList,",");
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

    /**
     * 티켓수 증가
     */
    public void addTicketCount() {
        this.ticketCount++;
    }

    /**
     * 티켓수 감소
     */
    public void subtractTicketCount() {
        this.ticketCount--;
    }

    /**
     * 티켓북 삭제시 총 티켓 수 감소*
     */
    public void subtractTotalTicketCount(Integer count){
        this.ticketCount-=count;

    }
    /**
     * 티켓 삭제시 member 총 좋아요수 감소
     */
    public void subtractTotalLikeCount(Integer count) {
        this.likeCount -= count;
    }
}
