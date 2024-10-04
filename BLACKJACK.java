import java.util.Random;
import java.util.Scanner;

public class BLACKJACK {

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    // Game Variables
    static int wager = 0;
    static int houseLimit = 500;
    static int[] deck = new int[52];
    static int[] playerHand = new int[5];
    static int[] dealerHand = new int[5];
    static int playerTotal = 0;
    static int dealerTotal = 0;

    public static void main(String[] args) {
        System.out.println("DO YOU WANT INSTRUCTIONS? (IF SO, TYPE 1)");
        int k = scanner.nextInt();

        if (k == 1) {
            showInstructions();
        }

        initializeGame();
        playGame();
    }

    public static void showInstructions() {
        System.out.println("THIS IS A GAME OF BLACKJACK - LAS VEGAS STYLE.");
        System.out.println("HERE ARE THE RULES OF THE HOUSE. THE DEALER");
        System.out.println("MUST HIT ON 16 OR LESS AND MUST STAY ON 17 OR MORE.");
        System.out.println("YOU MAY SPLIT TWO CARDS IF THEY ARE THE SAME.");
        System.out.println("ALSO, YOU MAY DOUBLE YOUR BET AND RECEIVE EXACTLY ONE MORE CARD.");
        System.out.println("WHEN THE DEALER HAS AN EXPOSED ACE, HE WILL ASK YOU FOR AN INSURANCE BET.");
        System.out.println("AN INSURANCE BET RISKS HALF YOUR BET FOR A WIN IF THE DEALER HAS BLACKJACK.");
        System.out.println("THE HOUSE LIMIT IS $500.");
        System.out.println("GOOD LUCK!");
    }

    public static void initializeGame() {
        for (int i = 0; i < deck.length; i++) {
            deck[i] = 0;
        }
        playerTotal = 0;
        dealerTotal = 0;
    }

    public static void playGame() {
        while (true) {
            System.out.println("WAGER?");
            wager = scanner.nextInt();

            if (wager > houseLimit) {
                System.out.println("THAT'S TOO MUCH - HOUSE LIMIT IS $500.");
                continue;
            }

            System.out.println("OK, HERE IS THE FIRST HAND.");
            dealCards();

            System.out.println("YOUR TOTAL IS: " + playerTotal);
            System.out.println("DEALER SHOWS: " + dealerHand[0]);

            if (playerTotal == 21) {
                System.out.println("BLACKJACK! YOU WIN!");
                return;
            }

            playerTurn();
            dealerTurn();
            checkWinner();
        }
    }

    public static void dealCards() {
        for (int i = 0; i < 2; i++) {
            playerHand[i] = drawCard();
            dealerHand[i] = drawCard();
        }
        playerTotal = calculateHandTotal(playerHand);
        dealerTotal = calculateHandTotal(dealerHand);
    }

    public static int drawCard() {
        return random.nextInt(13) + 1; // Simulate card from 1 to 13 (Ace to King)
    }

    public static int calculateHandTotal(int[] hand) {
        int total = 0;
        for (int card : hand) {
            if (card == 1) {
                total += (total + 11 <= 21) ? 11 : 1;
            } else if (card >= 10) {
                total += 10;
            } else {
                total += card;
            }
        }
        return total;
    }

    public static void playerTurn() {
        boolean playerDone = false;
        while (!playerDone) {
            System.out.println("WHAT WILL YOU DO? (0: STAY, 1: HIT, 2: DOUBLE)");
            int choice = scanner.nextInt();

            if (choice == 1) {
                int newCard = drawCard();
                System.out.println("YOU DREW A: " + newCard);
                playerTotal += calculateHandTotal(new int[]{newCard});
                System.out.println("YOUR TOTAL IS NOW: " + playerTotal);

                if (playerTotal > 21) {
                    System.out.println("BUSTED! YOU LOSE.");
                    playerDone = true;
                }
            } else if (choice == 2) {
                wager *= 2;
                int newCard = drawCard();
                System.out.println("YOU DOUBLED AND DREW A: " + newCard);
                playerTotal += calculateHandTotal(new int[]{newCard});
                System.out.println("YOUR TOTAL IS NOW: " + playerTotal);
                playerDone = true;
            } else {
                playerDone = true;
            }
        }
    }

    public static void dealerTurn() {
        System.out.println("DEALER'S TURN.");
        System.out.println("DEALER TOTAL IS: " + dealerTotal);

        while (dealerTotal < 17) {
            int newCard = drawCard();
            System.out.println("DEALER DREW A: " + newCard);
            dealerTotal += calculateHandTotal(new int[]{newCard});
            System.out.println("DEALER'S TOTAL IS NOW: " + dealerTotal);
        }
    }

    public static void checkWinner() {
        if (playerTotal > 21) {
            System.out.println("YOU BUSTED! DEALER WINS.");
        } else if (dealerTotal > 21) {
            System.out.println("DEALER BUSTED! YOU WIN.");
        } else if (playerTotal > dealerTotal) {
            System.out.println("YOU WIN! CONGRATULATIONS!");
        } else if (dealerTotal > playerTotal) {
            System.out.println("DEALER WINS! BETTER LUCK NEXT TIME.");
        } else {
            System.out.println("IT'S A TIE!");
        }
    }
}