
/*
 * Blackjack Game - Java Version
 * 
 * This is a simple command-line implementation of Blackjack (Las Vegas style).
 * The player can place a wager, and the game proceeds with standard Blackjack rules.
 * The dealer hits on 16 or less and stands on 17 or more.
 * Players can split a pair, double their bet, or choose to hit or stand.
 * The player wins if they get a higher total than the dealer without busting.
 *
 * Author: Mikael Amare
 * Date: 2024-10-22
 * Version: 1.0
*/

import java.util.Random;
import java.util.Scanner;

public class BLACKJACK {
    static int[] deck = new int[52];
    static int[] playerHand = new int[5];
    static int[] dealerHand = new int[5];
    static int wager;
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);
    
    static int playerBalance = 1000; // Starting balance

    public static void main(String[] args) {
        boolean playAgain = true;

        while (playAgain && playerBalance > 0) {
            System.out.println("Your current balance is: $" + playerBalance);
            System.out.println("DO YOU WANT INSTRUCTIONS (IF SO, TYPE 'I')?");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("I")) {
                printInstructions();
            }

            resetGame();
            playHand();

            if (playerBalance <= 0) {
                System.out.println("You're out of money! Game over.");
                break;
            }

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
        int numDecks = 6; // Standard for Vegas
        deck = new int[52 * numDecks];
        for (int counter = 0; counter < deck.length; counter++) {
            deck[counter] = counter % 13 + 1;
        }
        shuffleDeck();
    }
    
    public static void shuffleDeck() {
        for (int counter = 0; counter < deck.length; counter++) {
            int swapIndex = random.nextInt(deck.length);
            int temp = deck[counter];
            deck[counter] = deck[swapIndex];
            deck[swapIndex] = temp;
        }
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
    
    public static void playHand() {
        boolean validWager = false;

        // Place the wager
        while (!validWager) {
            try {
                System.out.println("\nPlace your wager:");
                wager = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (wager > playerBalance) {
                    System.out.println("\nYou cannot wager more than your balance! Current balance: $" + playerBalance);
                } else if (wager <= 0) {
                    System.out.println("\nWager must be greater than zero!");
                } else if (wager > 500) {
                    System.out.println("\nThat's too much - House limit is $500.");
                } else {
                    validWager = true; // Wager is valid
                }
            } catch (Exception e) {
                System.out.println("\nInvalid input! Please enter a valid wager as a whole number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        // Deal cards and check for Blackjack
        System.out.println("\nDealing cards...");
        dealCards();
        if (playerTotal() == 21 && playerHand[2] == 0) {
            System.out.println("\nBlackjack! You win!");
            playerBalance += wager * 1.5;
            return;
        }

        // Player's turn
        playerTurn();

        // Dealer's turn
        dealerTurn();

        // Determine winner
        determineWinner();
    }

    public static void playerTurn() {
        boolean playerBusted = false;
        int total = playerTotal();
        
        while (total < 21) {
            System.out.println("Your total is " + total + ". Hit (1), Double (2), Split (3) Stand (0)?");
            int action = scanner.nextInt();
            scanner.nextLine();  // consume newline
            
            if (action == 1) {
                int newCard = drawCard();
                System.out.println("You drew: " + cardString(newCard));
                total += cardValue(newCard);
            } else {
                break;
            }
            
            if (action == 2) {
                wager *= 2;
                int newCard = drawCard();
                System.out.println("You drew: " + cardString(newCard));
                total += cardValue(newCard);
                break; // Ends the turn after doubling down
            }

            if (action == 3) {
                splitPair();
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
    
    public static void splitPair() {
        if (playerHand[0] % 13 != playerHand[1] % 13) { // Not a pair
            System.out.println("You cannot split; your cards are not a pair.");
            return;
        }

        int[] splitHand1 = new int[5];
        int[] splitHand2 = new int[5];

        splitHand1[0] = playerHand[0];
        splitHand2[0] = playerHand[1];

        splitHand1[1] = drawCard();
        splitHand2[1] = drawCard();

        System.out.println("Split hand 1: " + cardString(splitHand1[0]) + ", " + cardString(splitHand1[1]));
        System.out.println("Split hand 2: " + cardString(splitHand2[0]) + ", " + cardString(splitHand2[1]));

        System.out.println("Playing hand 1...");
        playIndividualHand(splitHand1);

        System.out.println("Playing hand 2...");
        playIndividualHand(splitHand2);
    }

    public static void playIndividualHand(int[] hand) {
        int total = calculateTotal(hand);
        while (total < 21) {
            System.out.println("Your total is " + total + ". Hit (1) or Stand (0)?");
            int action = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (action == 1) {
                int newCard = drawCard();
                System.out.println("You drew: " + cardString(newCard));
                hand[findEmptySlot(hand)] = newCard;
                total = calculateTotal(hand);
            } else {
                break;
            }

            if (total > 21) {
                System.out.println("You busted with " + total + "!");
                return;
            }
        }
        System.out.println("You stand with " + total + ".");
    }

    public static int findEmptySlot(int[] hand) {
        for (int counter = 0; counter < hand.length; counter++) {
            if (hand[counter] == 0) {
                return counter;
            }
        }
        return -1; // Shouldn't happen in normal gameplay
    }

    public static int calculateTotal(int[] hand) {
        int total = 0;
        int aceCount = 0;
        for (int card : hand) {
            if (card == 1) {
                aceCount++;
                total += 11;
            } else if (card > 10) {
                total += 10;
            } else {
                total += card;
            }
        }
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
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
        int playerTotal = calculateTotal(playerHand);
        int dealerTotal = calculateTotal(dealerHand);

        if (playerTotal > 21) {
            System.out.println("You lose.");
            playerBalance -= wager;
        } else if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("You win!");
            playerBalance += wager;
        } else if (playerTotal == dealerTotal) {
            System.out.println("It's a tie!");
        } else {
            System.out.println("Dealer wins.");
            playerBalance -= wager;
        }
    }
}
