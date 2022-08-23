package return_a.tcat.dto.ticketbook;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Getter
public class TicketbookResDto {

    private List<TicketbookDto> ticketbooks;
    private String sequence;

    public List<TicketbookDto> sortTicketbooks(List<TicketbookDto> ticketbooks, String sequence) {
        String[] sequenceArray = sequence.split(",");
        List<String> sequenceList = new ArrayList<>(Arrays.asList(sequenceArray));

        List<TicketbookDto> results = new ArrayList<>();
        Iterator<String> s = sequenceList.iterator();

        while (s.hasNext()) {
            String order = s.next();
            Iterator<TicketbookDto> it = ticketbooks.iterator();
            while (it.hasNext()) {
                TicketbookDto ticketbook = it.next();
                if (order.equals(ticketbook.getId().toString())) {
                    results.add(ticketbook);
                }
            }
        }

        return results;

    }

    public TicketbookResDto(List<TicketbookDto> ticketbooks, String sequence) {
        this.ticketbooks = sortTicketbooks(ticketbooks, sequence);
        this.sequence = sequence;
    }

}
