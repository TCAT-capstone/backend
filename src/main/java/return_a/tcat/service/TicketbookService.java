package return_a.tcat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.Member;
import return_a.tcat.domain.Ticketbook;
import return_a.tcat.dto.ticketbook.TicketbookReqDto;
import return_a.tcat.repository.MemberRepository;
import return_a.tcat.repository.TicketbookRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketbookService {

    private final TicketbookRepository ticketbookRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(TicketbookReqDto ticketbookDto) {
        Member member = memberRepository.findOne(ticketbookDto.getMemberId());

        Ticketbook ticketbook = Ticketbook.builder()
                .name(ticketbookDto.getName())
                .build();

        ticketbook.setMember(member);
        ticketbookRepository.save(ticketbook);

        return ticketbook.getId();
    }

    @Transactional
    public Long saveDefault(Long memberId, String name) {
        Member member = memberRepository.findOne(memberId);

        Ticketbook ticketbook = Ticketbook.builder()
                .name(name)
                .build();

        ticketbook.setMember(member);
        ticketbookRepository.save(ticketbook);

        return ticketbook.getId();
    }


    public Ticketbook findTicketbook(Long ticketbookId){
        return ticketbookRepository.findOne(ticketbookId);
    }

    public List<Ticketbook> findTicketbooks(Long memberId) {
        return ticketbookRepository.findByMemberId(memberId);
    }

    @Transactional
    public void deleteById(Long id) {
        ticketbookRepository.deleteById(id);
    }

}
