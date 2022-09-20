package return_a.tcat.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follows_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String targetHomeId;

    public void setMember(Member member) {
        this.member = member;
        member.getFollows().add(this);
    }
    @Builder
    public Follows(Member member, String targetHomeId) {
        this.member = member;
        this.targetHomeId = targetHomeId;

    }
}
