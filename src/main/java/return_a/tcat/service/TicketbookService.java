package return_a.tcat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.Member;
import return_a.tcat.domain.Ticketbook;
import return_a.tcat.dto.ticketbook.TicketbookDto;
import return_a.tcat.dto.ticketbook.TicketbookReqDto;
import return_a.tcat.repository.MemberRepository;
import return_a.tcat.repository.TicketRepository;
import return_a.tcat.repository.TicketbookRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketbookService {

    private final TicketbookRepository ticketbookRepository;
    private final MemberRepository memberRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public Ticketbook save(TicketbookDto ticketbookDto, Long memberId) {
        Member member = memberRepository.findOne(memberId);
        Long tempId = ticketbookDto.getId();
        Ticketbook ticketbook = Ticketbook.builder()
                .name(ticketbookDto.getName())
                .ticketbookImg(ticketbookDto.getTicketbookImg())
                .description(ticketbookDto.getDescription())
                .build();

        ticketbook.setMember(member);
        ticketbookRepository.save(ticketbook);
        member.replaceTicketbookSequence(tempId, ticketbook.getId());
        return ticketbook;
    }

    @Transactional
    public Ticketbook updateTicketbook(TicketbookDto ticketbookDto) {

        Ticketbook ticketbook = ticketbookRepository.findOne(ticketbookDto.getId());
        ticketbook.changeTicketbook(ticketbookDto);

        return ticketbook;
    }

    @Transactional
    public List<Ticketbook> manageTicketbook(TicketbookReqDto ticketbookReqDto, Long memberId) {

        List<TicketbookDto> append = ticketbookReqDto.getAppend();
        List<TicketbookDto> update = ticketbookReqDto.getUpdate();
        List<TicketbookDto> delete = ticketbookReqDto.getDelete();

        deleteTicketbook(delete, memberId);

        Iterator<TicketbookDto> ap = append.iterator();
        Iterator<TicketbookDto> up = update.iterator();
        while (ap.hasNext()) {
            TicketbookDto ticketbookDto = ap.next();
            save(ticketbookDto, memberId);
        }

        while (up.hasNext()) {
            TicketbookDto ticketbookDto = up.next();
            updateTicketbook(ticketbookDto);
        }

        List<Ticketbook> ticketbooks = new ArrayList<>();
        Member member = memberRepository.findOne(memberId);
        ticketbooks = getTicketbook(member.getSequence());

        return ticketbooks;
    }

    public List<Ticketbook> getTicketbook(String sequence) {
        List<Ticketbook> ticketbooks = new ArrayList<>();

        String[] sequenceArray = sequence.split(",");
        List<String> sequenceList = new ArrayList<>(Arrays.asList(sequenceArray));

        List<TicketbookDto> results = new ArrayList<>();
        Iterator<String> s = sequenceList.iterator();

        while (s.hasNext()) {
            Long order = Long.valueOf(s.next());
            ticketbooks.add(ticketbookRepository.findOne(order));
        }

        return ticketbooks;

    }

    @Transactional
    public void deleteTicketbook(List<TicketbookDto> ticketbooksDto, Long memberId) {
        Member member = memberRepository.findOne(memberId);
        Iterator<TicketbookDto> it = ticketbooksDto.iterator();

        while (it.hasNext()) {
            TicketbookDto ticketbookDto = it.next();
            member.subtractTotalTicketCount(ticketRepository.findCountByTicketBook(ticketbookDto.getId()));
            member.subtractTotalLikeCount(ticketRepository.findLikeByTicketBook(ticketbookDto.getId()));
            ticketbookRepository.deleteById(ticketbookDto.getId());
        }
    }


    @Transactional
    public Long saveDefault(Long memberId) {
        Member member = memberRepository.findOne(memberId);

        Ticketbook ticketbook = Ticketbook.builder()
                .name("default")
                .ticketbookImg("")
                .description("기본 티켓북입니다.")
                .build();

        ticketbook.setMember(member);
        ticketbookRepository.save(ticketbook);
        member.changeTicketbookSequence(ticketbook.getId().toString());

        return ticketbook.getId();
    }

    public Ticketbook findTicketbook(Long ticketbookId) {
        return ticketbookRepository.findOne(ticketbookId);
    }

    public List<Ticketbook> findTicketbooks(String homeId) {
        return ticketbookRepository.findByHomeId(homeId);
    }


}
