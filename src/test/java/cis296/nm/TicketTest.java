package cis296.nm;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
class TicketTest {

    @Test
    void winnings() {
        Ticket winner = new Ticket(Arrays.asList(1, 2, 3, 4, 5), 6);

        assertAll(
                () -> assertEquals(0, Ticket.winnings(null, null)),
                () -> assertEquals(0, Ticket.winnings(new Ticket(), new Ticket())),

                () -> assertEquals(0, Ticket.winnings(winner, new Ticket(Arrays.asList(11, 12, 13, 14, 15), 16))),
                () -> assertEquals(4, Ticket.winnings(winner, new Ticket(Arrays.asList(11, 12, 13, 14, 15), 6))),

                () -> assertEquals(0, Ticket.winnings(winner, new Ticket(Arrays.asList(1, 12, 13, 14, 15), 16))),
                () -> assertEquals(4, Ticket.winnings(winner, new Ticket(Arrays.asList(1, 12, 13, 14, 15), 6))),

                () -> assertEquals(0, Ticket.winnings(winner, new Ticket(Arrays.asList(1, 2, 13, 14, 15), 16))),
                () -> assertEquals(7, Ticket.winnings(winner, new Ticket(Arrays.asList(1, 2, 13, 14, 15), 6))),

                () -> assertEquals(7, Ticket.winnings(winner, new Ticket(Arrays.asList(1, 2, 3, 14, 15), 16))),
                () -> assertEquals(100, Ticket.winnings(winner, new Ticket(Arrays.asList(1, 2, 3, 14, 15), 6))),

                () -> assertEquals(100, Ticket.winnings(winner, new Ticket(Arrays.asList(1, 2, 3, 4, 15), 16))),
                () -> assertEquals(50000, Ticket.winnings(winner, new Ticket(Arrays.asList(1, 2, 3, 4, 15), 6))),

                () -> assertEquals(1000000, Ticket.winnings(winner, new Ticket(Arrays.asList(1, 2, 3, 4, 5), 16))),
                () -> assertEquals(Ticket.JACKPOT, Ticket.winnings(winner, new Ticket(Arrays.asList(1, 2, 3, 4, 5), 6))),

                () -> assertEquals(Ticket.JACKPOT, Ticket.winnings(winner, new Ticket(Arrays.asList(5, 4, 3, 2, 1), 6)))
        );
    }

    @Test
    void tryParse() {
        Ticket output = new Ticket();

        assertAll(
                () -> assertFalse(Ticket.tryParse(null, null)),
                () -> assertFalse(Ticket.tryParse("", output)),
                () -> assertFalse(Ticket.tryParse("1 2 3 5 5 6", output)),
                () -> assertFalse(Ticket.tryParse("1 2 3 4 5", output)),
                () -> assertFalse(Ticket.tryParse("1 2 3 4 5 6 7 8", output)),
                () -> assertFalse(Ticket.tryParse("1 two 3 4 5 6", output)),
                () -> assertFalse(Ticket.tryParse("0 2 3 4 5 6", output)),
                () -> assertFalse(Ticket.tryParse("70 2 3 4 5 6", output)),
                () -> assertFalse(Ticket.tryParse("1 2 3 -4 5 6", output)),
                () -> assertFalse(Ticket.tryParse("1 2 3 4 5 27", output)),
                () -> assertTrue(Ticket.tryParse("1 2 3 4 5 6", output)),
                () -> assertTrue(Ticket.tryParse("1  2  3  4    5   6", output))
        );
    }
}