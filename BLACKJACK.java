import java.util.Random;
import java.util.Scanner;

public class Blackjack {
    static int[] deck = new int[52];
    static int[] playerHand = new int[5];
    static int[] dealerHand = new int[5];
    static int wager;
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        boolean playAgain = true;
        
        while (playAgain) {
            System.out.println("DO YOU WANT INSTRUCTIONS (IF SO, TYPE 'I')?");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("I")) {
                printInstructions();
            }
            
            resetGame();
            playHand();
            
            System.out.println("Do you want to play again? (Y/N)");
            playAgain = scanner.nextLine().equalsIgnoreCase("Y");
        }
    }
    
    public static void printInstructions() {
        System.out.println("This is a game of Blackjack. Las Vegas style.");
        System.out.println("The dealer must hit on 16 or less and stay on 17 or more.");
        System.out.println("You may split two cards if they are the same and play one hand with each.");
        System.out.println("You may double your bet and receive exactly one more card any time on your first hit.");
        System.out.println("Typing instructions are: 0 - No hit, 1 - Hit, 2 - Double, 3 - Split a pair.");
    }
    
    public static void resetGame() {
        for (int i = 0; i < deck.length; i++) {
            deck[i] = i % 13 + 1;  // Values between 1 and 13 (representing cards)
        }
        shuffleDeck();
    }
    
    public static void shuffleDeck() {
        for (int i = 0; i < deck.length; i++) {
            int swapIndex = random.nextInt(deck.length);
            int temp = deck[i];
            deck[i] = deck[swapIndex];
            deck[swapIndex] = temp;
        }
    }
    
    public static void playHand() {
        System.out.println("Place your wager:");
        wager = scanner.nextInt();
        scanner.nextLine();  // consume newline
        
        if (wager > 500) {
            System.out.println("That's too much - House limit is $500.");
            return;
        }
        
        System.out.println("Dealing cards...");
        dealCards();
        
        // Game logic for player and dealer turns
        if (playerTotal() == 21) {
            System.out.println("Blackjack! You win!");
        } else {
            playerTurn();
            dealerTurn();
        }
        
        determineWinner();
    }
    
    public static void dealCards() {
        playerHand[0] = drawCard();
        playerHand[1] = drawCard();
        dealerHand[0] = drawCard();
        dealerHand[1] = drawCard();
        
        System.out.println("Your first card: " + cardString(playerHand[0]));
        System.out.println("Your second card: " + cardString(playerHand[1]));
        System.out.println("Dealer shows: " + cardString(dealerHand[0]));
    }
    
    public static int drawCard() {
        int cardIndex = random.nextInt(deck.length);
        int card = deck[cardIndex];
        deck[cardIndex] = 0;  // Mark the card as used
        return card;
    }
    
    public static String cardString(int card) {
        switch (card) {
            case 1: return "Ace";
            case 11: return "Jack";
            case 12: return "Queen";
            case 13: return "King";
            default: return String.valueOf(card);
        }
    }
    
    public static int playerTotal() {
        return cardValue(playerHand[0]) + cardValue(playerHand[1]);
    }
    
    public static int cardValue(int card) {
        if (card > 10) return 10;  // Jack, Queen, King are all worth 10
        return card;
    }
    
    public static void playerTurn() {
        boolean playerBusted = false;
        int total = playerTotal();
        
        while (total < 21) {
            System.out.println("Your total is " + total + ". Hit (1) or Stand (0)?");
            int action = scanner.nextInt();
            scanner.nextLine();  // consume newline
            
            if (action == 1) {
                int newCard = drawCard();
                System.out.println("You drew: " + cardString(newCard));
                total += cardValue(newCard);
            } else {
                break;
            }
            
            if (total > 21) {
                System.out.println("You busted with " + total + "!");
                playerBusted = true;
                break;
            }
        }
        
        if (!playerBusted) {
            System.out.println("You stand with " + total + ".");
        }
    }
    
    public static void dealerTurn() {
        int total = cardValue(dealerHand[0]) + cardValue(dealerHand[1]);
        System.out.println("Dealer's hidden card: " + cardString(dealerHand[1]));
        
        while (total < 17) {
            int newCard = drawCard();
            System.out.println("Dealer draws: " + cardString(newCard));
            total += cardValue(newCard);
        }
        
        if (total > 21) {
            System.out.println("Dealer busted with " + total + "!");
        } else {
            System.out.println("Dealer stands with " + total + ".");
        }
    }
    
    public static void determineWinner() {
        int playerTotal = cardValue(playerHand[0]) + cardValue(playerHand[1]);
        int dealerTotal = cardValue(dealerHand[0]) + cardValue(dealerHand[1]);
        
        if (playerTotal > 21) {
            System.out.println("You lose.");
        } else if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("You win!");
        } else if (playerTotal == dealerTotal) {
            System.out.println("It's a tie!");
        } else {
            System.out.println("Dealer wins.");
        }
    }
}
