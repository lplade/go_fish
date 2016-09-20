package name.lplade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    //Create two scanners, one for Strings, and one for numbers - int and float values.
    static Scanner stringScanner = new Scanner(System.in);
    static Scanner numberScanner = new Scanner(System.in);


    public static void main(String[] args) {

        //Generate a new deck of cards
        LinkedList deck = newDeck(false);
        //Shuffle the deck
        deck = shuffleDeck(deck);

        /*
        //simple arrays to keep track of how many of each card in hand. Facevalue
        int[] playerHand = new int[14];
        int[] playerBooks = new int[14];
        */

        ArrayList playerHand = new ArrayList();
        ArrayList cpuHand = new ArrayList();
        int[] playerBook = new int[14];
        int[] cpuBook = new int[14];

        //Deal out seven cards to each player
        System.out.println("Dealing cards...");
        for (int i = 0; i < 7; i++) {
            Object newCard;
            newCard = deck.pop();
            playerHand.add(newCard);
            newCard = deck.pop();
            cpuHand.add(newCard);
        }

        //TODO convert remaining LinkedList into unsorted pool of cards now?

        Boolean endGame = false; //sentinel
        int cpuAsk = 0; //for rudimentary AI

        //Main game loop
        do {
            //Run a turn

            System.out.println("Your cards: A 2 3 4 5 6 7 8 9 J Q K");
            System.out.printf("            %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d\n", showCards(handToTable(playerHand)));
            System.out.printf("     BOOKS: %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d\n", showCards(playerBook));
            System.out.println();

            //For debugging, obviously
            System.out.println("  My cards: A 2 3 4 5 6 7 8 9 J Q K");
            System.out.printf("            %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d\n", showCards(handToTable(cpuHand)));
            System.out.printf("     BOOKS: %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d\n", showCards(cpuBook));
            System.out.println();

            Boolean legalPick = false;
            while (!legalPick) {
                System.out.print("Which card are you looking for? ");
                String seekCard = stringScanner.nextLine().toUpperCase();
                //TODO parses string input and converts it into an array index value
                //if it's no good, keep asking, otherwise
                legalPick = true;
            }

            //todo loop through cpu cards to see if match exists,
            //if so remove all from cpu hand and add it to my hand
            //otherwise, Fish a new card ()
            //if fished card is seekCard, then player gets another turn
            //TODO wrap player turn in its own while loop for bonus turns
            //check player hand to see if player has four of any card
            //if so, move those cards into player book

            //TODO CPU turn
            //Select a seek card (initial algo: increment cpuAsk by 1 each turn)
            //Print a message about card asking
            //Automatically seek through player hand
            //if match, remove all from player hand and add to cpu hand
            //otherwise, fish a new card ()
            //if fished card is seekCard, then cpu gets another turn
            //TODO wrap cpu turn in its own while loop for bonus turns
            //check cpu hand to see if cpu has four of any card
            //if so, move those cards into cpu book


            endGame = true;
            for (int i = 1; i < 14; i++) {
                if (!(playerBook[i] == 4 || cpuBook[i] == 4)) {
                    endGame = false; //keep playing until all books are made
                }
            }
        } while (!endGame);

        int playerScore =0;
        int cpuScore = 0;
        for (int i = 1; i < 14; i++) {
            if (playerBook[i] == 4) playerScore++;
            if (cpuBook[i] == 4) cpuScore++;
        }
        System.out.println();


        // Close scanners. Good practice to clean up resources you use.
        // Don't try to use scanners after this point. All code that uses scanners goes above here.
        stringScanner.close();
        numberScanner.close();
    }

    private static LinkedList newDeck(Boolean jokers) {
        // Use this to generate an array containing all 52/54 cards

        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        //int[] cardValues = new int[14];
        //for (int i = 0; i < cardValues.length; i++){
        //    cardValues[i] = i;
        //}
        // cardValue as a simple index for card value
        // 0 = joker
        // 1 = ace
        // 2 = two
        // ...
        // 10 = ten
        // 11 = jack
        // 12 = queen
        // 13 = king

        //populate the initial card deck
        //each card is 2-element array of {value, suit}

        LinkedList cardDeck = new LinkedList<>();
        for (String suit : suits) {
            for (int cardValue = 1; cardValue < 14; cardValue++) {
                Object[] card = {cardValue, suit};
                cardDeck.add(card);
            }
        }
        if (jokers) { //add two jokers
            Object[] card = {0, "WILD"};
            for (int i = 0; i < 2; i++)
                cardDeck.add(card);
        }

        return cardDeck;
    }

    private static LinkedList shuffleDeck(LinkedList cardDeck) {

        //use Collections method to randomize order

        Collections.shuffle(cardDeck);
        return cardDeck;

    }

    private static void displayCard(Object[] card) {
        //print a card in human readable format on console
        Integer value = (Integer) card[0];
        String suit = (String) card[1];

        //TODO logic that actually decodes above described index into face cards and such
        //for now, just show that index value

        System.out.printf("%d of %s\n", value, suit);

    }

    public static int getCardValInt(Object[] card) {
        //returns the value of the "card" Object as index integer
        Integer value = (Integer) card[0];
        return value;
    }

    private static String cardIntToStr(int cardVal) {
        //this decodes a card index value into a string describing the card
        //TODO some logic
        return null;
    }

    private static String getCardSuit(Object[] card) {
        //returns the suit of a "card" Object
        String suit = (String) card[1];
        return suit;
    }

    private static int[] handToTable(ArrayList<Object> hand){
        //returns a hand ArrayList as an int array of how many card values
        int[] cardTotals = new int[14];
        for(Object card : hand) {
            int thisCardValue = getCardValInt((Object[]) card);
            //increment that "slot" in the array. ignore suit.
            cardTotals[thisCardValue]++;
        }
        return cardTotals;
    }

    private static Object[] showCards(int[] cardInts){
        //apparently, we can't pass an array of ints into string formatter
        //here, we convert them into something the string formatter accepts
        //http://stackoverflow.com/questions/5606338/cast-primitive-type-array-into-object-array-in-java
        Object[] cardObjs = new Object[cardInts.length];
        for (int i = 0; i < cardInts.length; i++){
            cardObjs[i] = cardInts[i];
        }
        return cardObjs;
    }

}

