/*
Input Methods File,
No Testing here
*/

package cis296.nm;

import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    private static int WON = 0;
    private static int SPENT = 0;

    public static void single() {
        Ticket winner = Ticket.random();
        Ticket ticket = new Ticket();

        SPENT += 2;

        while (true) {
            System.out.print("Enter Your Six Numbers: ");

            if (Ticket.tryParse(SCANNER.nextLine(), ticket)) {
                break;
            }

            System.out.println("Invalid Input, Try Again...");
        }

        int winnings = Ticket.winnings(winner, ticket);

        WON += winnings;

        System.out.println("Winning Ticket: " + winner);
        System.out.println("You Won $" + winnings);
    }

    public static void multi() {
        Ticket winner = Ticket.random();
        int amount = -1;

        while (true) {
            System.out.print("How Many Tickets: ");
            String line = SCANNER.nextLine().trim();

            try {
                amount = Integer.parseInt(line);

                if (amount <= 0) {
                    throw new Exception();
                }

                break;
            } catch (Exception _) {}

            System.out.println("Invalid Input, Try Again...");
        }

        int totalWinnings = 0;
        int totalSpent = amount * 2;

        SPENT += totalSpent;

        while (amount-- != 0) {
            Ticket ticket = Ticket.random();
            int winnings = Ticket.winnings(winner, ticket);

            WON += winnings;
            totalWinnings += winnings;

            if (winnings != 0) {
                System.out.println("Ticket Won $" + winnings + " -> " + ticket);
            }
        }

        System.out.println("Winning Ticket: " + winner);
        System.out.println("You Spent $" + totalSpent + " & Won $" + totalWinnings + "; d$ = " + (totalWinnings - totalSpent));
    }

    public static void main(String[] args) {
        boolean shouldQuit = false;

        while (!shouldQuit) {
            System.out.println("\t1) Single \"Pick Your Numbers\" (Default)");
            System.out.println("\t2) Multiple Random Tickets");
            System.out.println("\tq) Quit");

            System.out.print("Choice ~: ");
            String line = SCANNER.nextLine().toLowerCase().trim();

            if (line.isEmpty()) {
                line = "1";
            }

            switch (line.charAt(0)) {
                case '1':
                    single();
                    break;
                case '2':
                    multi();
                    break;
                case 'q':
                    shouldQuit = true;
                    break;
            }
        }

        System.out.println("\nYou've Spent A Total Of $" + SPENT);
        System.out.println("You've Won A Total Of $" + WON);
        System.out.println("You've " + ((WON > SPENT) ? "Made" : "Lost") + " $" + Math.abs(SPENT - WON));
    }
}