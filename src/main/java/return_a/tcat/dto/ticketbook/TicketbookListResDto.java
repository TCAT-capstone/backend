package return_a.tcat.dto.ticketbook;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Getter
public class TicketbookListResDto {

    private List<TicketbookDto> ticketbooks;
    private String sequence;

    public void sortTicketbooks(String sequence) {
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

        this.ticketbooks =results;
    }

    public TicketbookListResDto(List<TicketbookDto> ticketbooks, String sequence) {
        this.ticketbooks = ticketbooks;
        this.sequence = sequence;
    }
}
