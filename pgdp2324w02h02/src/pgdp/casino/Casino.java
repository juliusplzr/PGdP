package pgdp.casino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Casino {

	public static void penguBlackJack() {

		// Here is a card deck for your games :)
		// Remember for testing you can use seeds:
		// CardDeck deck = CardDeck.getDeck(420);
		CardDeck deck = CardDeck.getDeck(420);

		// Initial player token amount
		int token = 1000;

		// Greeting at start of game
		System.out.println("Welcome to Pengu-BlackJack!");

		// Start of continuous playing until break/return statement
		while (true) {

			// Ask player whether to start/continue or exit
			while (true) {
				// Game options
				System.out.println("(1) Start a game or (2) exit");

				// User input
				int input = readInt();

				// Game options
				if (input == 2) {
					// Terminate game
					System.out.println("Your final balance: " + token);
					String s = token > 1000 ? "Wohooo! Ez profit! :D" : "That's very very sad :(";
					System.out.println(s);
					System.out.println("Thank you for playing. See you next time.");
					return;
				} else if (input == 1) {
					// Start/continue game
					break;
				} else {
					// Invalid argument
					System.out.println("What?!");
				}
			}

			// Play game
			// Print token balance
			System.out.println("Your current balance: " + token);

			// Input amount of tokens for bet
			int bet_amount = 0;

			// Check for valid amount of used tokens
			while (!(bet_amount > 0) || bet_amount > token) {
				System.out.println("How much do you want to bet?");
				bet_amount = readInt();
			}

			// Print player card details
			System.out.println("Player cards:");

			// Initialize card counter, player total
			int card_counter = 0;
			int player_total = 0;

			// Draw 1st card, print and add to player total
			int card = deck.drawCard();
			card_counter++;
			System.out.println(card_counter + " : " + card);
			player_total += card;

			// Draw 2nd card, print and add to player total
			card = deck.drawCard();
			card_counter++;
			System.out.println(card_counter + " : " + card);
			player_total += card;

			// Initialize and print player's card value
			System.out.println("Current standing: " + player_total);

			// Play until busting 21

				// Player's draw/stand decision
				while (player_total < 21) {
					System.out.println("(1) draw another card or (2) stay");
					int decision = readInt();

					if (decision == 1) {
						card = deck.drawCard();
						player_total += card;
						card_counter++;
						System.out.println(card_counter + " : " + card);
						System.out.println("Current standing: " + player_total);
					} else if (decision == 2) {
						break;
					} else {
						System.out.println("What?!");
					}
				}

				if (player_total > 21) {
					System.out.println("You lost " + bet_amount + " tokens.");
					token -= bet_amount;
				} else if (player_total == 21) {
					System.out.println("Blackjack! You won " + (2 * bet_amount) + " tokens.");
					token = token + 2 * bet_amount;
				} else {
					// Initialize dealer's card counter/value
					int dealer_total = 0;
					int card_counter_dealer = 0;

					// Announcing dealer's turn
					System.out.println("Dealer cards:");

					// Draw until dealer's card value is > than player's card value OR > 21
					while (true) {
						if (dealer_total > player_total) {
							break;
						} else {
							card = deck.drawCard();
							dealer_total += card;
							card_counter_dealer += 1;
							System.out.println(card_counter_dealer + " : " + card);
						}
					}

					// Print dealer's card value
					System.out.println("Dealer: " + dealer_total);

					// Check if dealer busted
					if (dealer_total > 21) {
						System.out.println("You won " + bet_amount + " tokens.");
						token += bet_amount;
					} else if (dealer_total > player_total) {
						System.out.println("Dealer wins. You lost " + bet_amount + " tokens.");
						token -= bet_amount;
					}
				}

			// Check amount of remaining token for non-negativity
			if (token == 0) {
				System.out.println("Sorry, you are broke. Better Luck next time.");
				System.out.println("Your final balance: " + token);
				System.out.println("That's very very sad :(");
				System.out.println("Thank you for playing. See you next time.");
				return;
			}
		}
	}

	public static String readString() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int readInt() {
		while (true) {
			String input = readString();
			try {
				return Integer.parseInt(input);
			} catch (Exception e) {
				System.out.println("This was not a valid number, please try again.");
			}
		}
	}

	public static void main(String[] args) {
		penguBlackJack();
	}

}