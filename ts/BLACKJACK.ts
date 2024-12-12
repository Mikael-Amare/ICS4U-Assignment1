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
        console.log(Current balance: $${this.playerBalance}. Place your wager:`);
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
        return this.deck.pop() || 0; // Safety check if deck is empty
    }

    static cardString(cardValue: number): string {
        const cardNames = ["Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"];
        return cardNames[(cardValue - 1) % 13];
    }

    static calculateHandValue(hand: number[]): number {
        let value = 0;
        let aces = 0;

        for (const card of hand) {
            if (card > 10) {
                value += 10; // Face cards are worth 10
            } else if (card === 1) {
                aces++;
                value += 11; // Initially count Ace as 11
            } else {
                value += card;
            }
        }

        while (value > 21 && aces > 0) {
            value -= 10; // Convert Ace from 11 to 1
            aces--;
        }

        return value;
    }

    static playerTurn(): void {
        while (true) {
            console.log("Your hand:", this.playerHand.map(this.cardString).join(", "));
            console.log("Your hand value:", this.calculateHandValue(this.playerHand));

            const choice = prompt("Enter 0 to Stay, 1 to Hit, or 2 to Double:");
            if (choice === "0") {
                console.log("You chose to stay.");
                break;
            } else if (choice === "1") {
                this.playerHand.push(this.drawCard());
                console.log("You hit and drew a card.");
                if (this.calculateHandValue(this.playerHand) > 21) {
                    console.log("You busted!");
                    break;
                }
            } else if (choice === "2") {
                console.log("You chose to double down.");
                this.wager *= 2;
                this.playerHand.push(this.drawCard());
                console.log("Your hand:", this.playerHand.map(this.cardString).join(", "));
                break;
            } else {
                console.log("Invalid input. Please try again.");
            }
        }
    }

    static dealerTurn(): void {
        console.log("Dealer's turn. Dealer reveals:", this.cardString(this.dealerHand[1]));
        while (this.calculateHandValue(this.dealerHand) < 17) {
            this.dealerHand.push(this.drawCard());
            console.log("Dealer hits and draws a card.");
        }

        console.log("Dealer's hand:", this.dealerHand.map(this.cardString).join(", "));
        console.log("Dealer's hand value:", this.calculateHandValue(this.dealerHand));
    }

    static determineWinner(): void {
        const playerValue = this.calculateHandValue(this.playerHand);
        const dealerValue = this.calculateHandValue(this.dealerHand);

        if (playerValue > 21) {
            console.log("You busted! Dealer wins.");
            this.playerBalance -= this.wager;
        } else if (dealerValue > 21 || playerValue > dealerValue) {
            console.log("You win!");
            this.playerBalance += this.wager;
        } else if (playerValue < dealerValue) {
            console.log("Dealer wins.");
            this.playerBalance -= this.wager;
        } else {
            console.log("It's a tie!");
        }
    }
`
