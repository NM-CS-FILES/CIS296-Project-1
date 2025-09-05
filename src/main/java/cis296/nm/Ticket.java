package cis296.nm;

import java.io.StringWriter;
import java.util.*;

public class Ticket {
    private static final Random RANDOM = new Random();

    private static final int WHITE_MIN = 1;
    private static final int WHITE_MAX = 69;
    private static final int WHITE_COUNT = 5;

    private static final int RED_MIN = 1;
    private static final int RED_MAX = 26;
    private static final int RED_COUNT = 1;

    public static final int JACKPOT = 1800000000;

    private final HashSet<Integer> whites;
    private int powerball;

    public Ticket() {
        this(new ArrayList<>(), 0);
    }

    public Ticket(List<Integer> whites, int powerball) {
        this.whites = new HashSet<>(whites);
        this.powerball = powerball;
    }

    @Override
    public String toString() {
        // it would be nice to test this
        // but hashset isn't ordered,
        // and I couldn't be bothered

        StringWriter writer = new StringWriter();

        for (Integer value : this.whites) {
            writer.append(value.toString());
            writer.append(' ');
        }

        writer.append(((Integer)this.powerball).toString());

        return writer.toString();
    }

    public static Ticket random() {
        Ticket ticket = new Ticket();

        for (int i = 0; i != WHITE_COUNT; i++ ) {
            int value;

            do {
                value = RANDOM.nextInt(WHITE_MIN, WHITE_MAX + 1);
            } while (ticket.whites.contains(value));

            ticket.whites.add(value);
        }

        ticket.powerball = RANDOM.nextInt(RED_MIN, RED_MAX + 1);

        return ticket;
    }

    public static int winnings(Ticket winning, Ticket held) {
        if (winning == null || held == null) {
            return 0;
        }

        if (winning.whites.size() != WHITE_COUNT || held.whites.size() != WHITE_COUNT) {
            return 0;
        }

        int whiteMatches = 0;
        boolean powerBallMatch = (winning.powerball == held.powerball);

        for (Integer value : winning.whites) {
            if (held.whites.contains(value)) {
                ++whiteMatches;
            }
        }

        return switch (whiteMatches) {
            case 0, 1 -> powerBallMatch ? 4 : 0;
            case 2    -> powerBallMatch ? 7 : 0;
            case 3    -> powerBallMatch ? 100 : 7;
            case 4    -> powerBallMatch ? 50000 : 100;
            case 5    -> powerBallMatch ? JACKPOT : 1000000;
            default   -> 0;
        };
    }

    public static boolean tryParse(String in, Ticket out) {
        if (in == null || out == null) {
            return false;
        }

        String[] parts = in.split(" ");
        Vector<Integer> values = new Vector<>();

        for (int i = 0; i != parts.length; i++) {
            String part = parts[i].trim();

            if (part.isEmpty()) {
                continue;
            }

            try {
                values.add(Integer.parseInt(part));
            } catch (Exception e) {
                return false;
            }
        }

        if (values.size() != WHITE_COUNT + RED_COUNT) {
            return false;
        }

        out.whites.clear();

        for (int i = 0; i != WHITE_COUNT; i++) {
            int value = values.elementAt(i);

            if (value < WHITE_MIN || value > WHITE_MAX || out.whites.contains(value)) {
                return false;
            }

            out.whites.add(value);
        }

        int powerball = values.elementAt(WHITE_COUNT);

        if (powerball < RED_MIN || powerball > RED_MAX) {
            return false;
        }

        out.powerball = powerball;

        return true;
    }
}