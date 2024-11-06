class Blackjack {
    static deck: number[] = [];
    static playerHand: number[] = [];
    static dealerHand: number[] = [];
    static wager: number = 0;
    static playerBalance: number = 1000;  // Initial player balance

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
        console.log("Welcome to Blackjack - Las Vegas style.");
        console.log("The dealer hits on 16 or less and stays on 17 or more.");
        console.log("You may double your bet on your first hit and receive exactly one more card.");
        console.log("Instructions: 0 - No hit, 1 - Hit, 2 - Double.");
    }

    static resetGame(): void {
        this.playerHand = [];
        this.dealerHand = [];

        if (this.deck.length < 15) {
            this.deck = Array.from({ length: 52 }, (_, counter) => (counter % 13) + 1); // Values 1-13
            this.shuffleDeck();
        }
    }

    static shuffleDeck(): void {
        for (let counter = 0; counter < this.deck.length; counter) {
            const swapIndex = Math.floor(Math.random() * this.deck.length);
            [this.deck[counter], this.deck[swapIndex]] = [this.deck[swapIndex], this.deck[counter]];
        }
    }

    static playHand(): void {
        console.log(`Current balance: $${this.playerBalance}. Place your wager:`);
        const wagerInput = prompt("Enter wager:");
        this.wager = parseInt(wagerInput || "0");
        this.dealCards();

        if (this.calculateHandValue(this.playerHand) === 21) {
            console.log("Blackjack! You win!");
            this.playerBalance += this.wager * 1.5;
            return;
        } else {
            this.playerTurn();
            if (this.calculateHandValue(this.playerHand) <= 21) {
                this.dealerTurn();
            }
            this.determineWinner();
        }
    }

    static dealCards(): void {
        this.playerHand.push(this.drawCard(), this.drawCard());
        this.dealerHand.push(this.drawCard(), this.drawCard());

        console.log("Your cards:", this.cardString(this.playerHand[0]), "and", this.cardString(this.playerHand[1]));
        console.log("Dealer shows:", this.cardString(this.dealerHand[0]));
    }

    static drawCard(): number {
        return this.deck.splice(Math.floor(Math.random() * this.deck.length), 1)[0];
    }

    static cardString(card: number): string {
        return card === 1 ? "Ace" : card === 11 ? "Jack" : card === 12 ? "Queen" : card === 13 ? "King" : card.toString();
    }

    static calculateHandValue(hand: number[]): number {
        let total = hand.reduce((sum, card) => sum + (card > 10 ? 10 : card), 0);
        let aces = hand.filter(card => card === 1).length;

        while (total <= 11 && aces > 0) {
            total += 10;
            aces--;
        }
        return total;
    }

    static playerTurn(): void {
        let playerBusted = false;

        while (this.calculateHandValue(this.playerHand) < 21) {
            console.log("Your total is", this.calculateHandValue(this.playerHand), "- Hit (1), Stand (0), or Double (2)");
            const actionInput = prompt("Enter action:");
            const action = parseInt(actionInput || "0");

            if (action === 1) {
                const newCard = this.drawCard();
                console.log("You drew:", this.cardString(newCard));
                this.playerHand.push(newCard);
            } else if (action === 2) {
                this.wager *= 2;
                console.log("You doubled down.");
                this.playerHand.push(this.drawCard());
                break;
            } else {
                break;
            }

            if (this.calculateHandValue(this.playerHand) > 21) {
                console.log("You busted with", this.calculateHandValue(this.playerHand));
                playerBusted = true;
                break;
            }
        }

        if (!playerBusted) console.log("You stand with", this.calculateHandValue(this.playerHand));
    }

    static dealerTurn(): void {
        let dealerTotal = this.calculateHandValue(this.dealerHand);
        console.log("Dealer's hidden card:", this.cardString(this.dealerHand[1]));

        while (dealerTotal < 17) {
            const newCard = this.drawCard();
            console.log("Dealer draws:", this.cardString(newCard));
            this.dealerHand.push(newCard);
            dealerTotal = this.calculateHandValue(this.dealerHand);
        }

        console.log(dealerTotal > 21 ? "Dealer busted!" : "Dealer stands with " + dealerTotal);
    }


    static determineWinner(): void {
        const playerTotal = this.calculateHandValue(this.playerHand);
        const dealerTotal = this.calculateHandValue(this.dealerHand);

        if (playerTotal > 21) {
            console.log("You lose.");
            this.playerBalance -= this.wager;
        } else if (dealerTotal > 21 || playerTotal > dealerTotal) {
            console.log("You win!");
            this.playerBalance += this.wager;
        } else if (playerTotal === dealerTotal) {
            console.log("It's a tie!");
        } else {
            console.log("Dealer wins.");
            this.playerBalance -= this.wager;
        }
    }
}

Blackjack.main();
