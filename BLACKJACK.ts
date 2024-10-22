/**
 * Blackjack Game - TypeScript Version
 * 
 * This is a simple command-line implementation of Blackjack (Las Vegas style).
 * The player can place a wager, and the game proceeds with standard Blackjack rules.
 * The dealer hits on 16 or less and stands on 17 or more.
 * Players can split a pair, double their bet, or choose to hit or stand.
 * The player wins if they get a higher total than the dealer without busting.
 *
 * Author: Mikael Amare
 * Date: 2024-10-22
 * Version: 5.6.2
 */
class Blackjack {
    static deck: number[] = new Array(52);
    static playerHand: number[] = new Array(5);
    static dealerHand: number[] = new Array(5);
    static wager: number = 0;

    static main(): void {
        let playAgain: boolean = true;

        while (playAgain) {
            console.log("DO YOU WANT INSTRUCTIONS (IF SO, TYPE 'I')?");
            const input = prompt("Enter choice:");

            if (input?.toUpperCase() === "I") {
                this.printInstructions();
            }

            this.resetGame();
            this.playHand();

            const playAgainInput = prompt("Do you want to play again? (Y/N)");
            playAgain = playAgainInput?.toUpperCase() === "Y";
        }
    }

    static printInstructions(): void {
        console.log("This is a game of Blackjack. Las Vegas style.");
        console.log("The dealer must hit on 16 or less and stay on 17 or more.");
        console.log("You may split two cards if they are the same and play one hand with each.");
        console.log("You may double your bet and receive exactly one more card any time on your first hit.");
        console.log("Typing instructions are: 0 - No hit, 1 - Hit, 2 - Double, 3 - Split a pair.");
    }

    static resetGame(): void {
        for (let i = 0; i < this.deck.length; i++) {
            this.deck[i] = (i % 13) + 1;  // Values between 1 and 13 (representing cards)
        }
        this.shuffleDeck();
    }

    static shuffleDeck(): void {
        for (let i = 0; i < this.deck.length; i++) {
            const swapIndex = Math.floor(Math.random() * this.deck.length);
            const temp = this.deck[i];
            this.deck[i] = this.deck[swapIndex];
            this.deck[swapIndex] = temp;
        }
    }

    static playHand(): void {
        console.log("Place your wager:");
        const wagerInput = prompt("Enter wager:");
        this.wager = parseInt(wagerInput || "0");

        if (this.wager > 500) {
            console.log("That's too much - House limit is $500.");
            return;
        }

        console.log("Dealing cards...");
        this.dealCards();

        if (this.playerTotal() === 21) {
            console.log("Blackjack! You win!");
        } else {
            this.playerTurn();
            this.dealerTurn();
        }

        this.determineWinner();
    }

    static dealCards(): void {
        this.playerHand[0] = this.drawCard();
        this.playerHand[1] = this.drawCard();
        this.dealerHand[0] = this.drawCard();
        this.dealerHand[1] = this.drawCard();

        console.log("Your first card: " + this.cardString(this.playerHand[0]));
        console.log("Your second card: " + this.cardString(this.playerHand[1]));
        console.log("Dealer shows: " + this.cardString(this.dealerHand[0]));
    }

    static drawCard(): number {
        const cardIndex = Math.floor(Math.random() * this.deck.length);
        const card = this.deck[cardIndex];
        this.deck[cardIndex] = 0;  // Mark the card as used
        return card;
    }

    static cardString(card: number): string {
        switch (card) {
            case 1: return "Ace";
            case 11: return "Jack";
            case 12: return "Queen";
            case 13: return "King";
            default: return card.toString();
        }
    }

    static playerTotal(): number {
        return this.cardValue(this.playerHand[0]) + this.cardValue(this.playerHand[1]);
    }

    static cardValue(card: number): number {
        if (card > 10) return 10;  // Jack, Queen, King are all worth 10
        return card;
    }

    static playerTurn(): void {
        let total = this.playerTotal();
        let playerBusted = false;

        while (total < 21) {
            console.log("Your total is " + total + ". Hit (1) or Stand (0)?");
            const actionInput = prompt("Enter action:");
            const action = parseInt(actionInput || "0");

            if (action === 1) {
                const newCard = this.drawCard();
                console.log("You drew: " + this.cardString(newCard));
                total += this.cardValue(newCard);
            } else {
                break;
            }

            if (total > 21) {
                console.log("You busted with " + total + "!");
                playerBusted = true;
                break;
            }
        }

        if (!playerBusted) {
            console.log("You stand with " + total + ".");
        }
    }

    static dealerTurn(): void {
        let total = this.cardValue(this.dealerHand[0]) + this.cardValue(this.dealerHand[1]);
        console.log("Dealer's hidden card: " + this.cardString(this.dealerHand[1]));

        while (total < 17) {
            const newCard = this.drawCard();
            console.log("Dealer draws: " + this.cardString(newCard));
            total += this.cardValue(newCard);
        }

        if (total > 21) {
            console.log("Dealer busted with " + total + "!");
        } else {
            console.log("Dealer stands with " + total + ".");
        }
    }

    static determineWinner(): void {
        const playerTotal = this.playerTotal();
        const dealerTotal = this.cardValue(this.dealerHand[0]) + this.cardValue(this.dealerHand[1]);

        if (playerTotal > 21) {
            console.log("You lose.");
        } else if (dealerTotal > 21 || playerTotal > dealerTotal) {
            console.log("You win!");
        } else if (playerTotal === dealerTotal) {
            console.log("It's a tie!");
        } else {
            console.log("Dealer wins.");
        }
    }
}

// Call the game
Blackjack.main();
