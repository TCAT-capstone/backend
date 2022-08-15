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
    public Long save(TicketbookReqDto ticketbookDto,Long memberId) {
        Member member = memberRepository.findOne(memberId);

        Ticketbook ticketbook = Ticketbook.builder()
                .name(ticketbookDto.getName())
                .build();

        ticketbook.setMember(member);
        ticketbookRepository.save(ticketbook);

        return ticketbook.getId();
    }

    @Transactional
    public Long saveDefault(Long memberId) {
        Member member = memberRepository.findOne(memberId);

        Ticketbook ticketbook = Ticketbook.builder()
                .name("default")
                .ticketbookImg(null)
                .build();

        ticketbook.setMember(member);
        ticketbookRepository.save(ticketbook);
        return ticketbook.getId();
    }


    public Ticketbook findTicketbook(Long ticketbookId){
        return ticketbookRepository.findOne(ticketbookId);
    }

    public List<Ticketbook> findTicketbooks(String homeId) {
        return ticketbookRepository.findByHomeId(homeId);
    }

    @Transactional
    public void deleteById(Long id) {
        ticketbookRepository.deleteById(id);
    }

}
