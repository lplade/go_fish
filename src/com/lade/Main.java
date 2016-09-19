package com.lade;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.Scanner;

public class Main {

    //Create two scanners, one for Strings, and one for numbers - int and float values.

    //Use this scanner to read text data that will be stored in String variables
    static Scanner stringScanner = new Scanner(System.in);
    //Use this scanner to read in numerical data that will be stored in int or double variables
    static Scanner numberScanner = new Scanner(System.in);

    //set up randomizer
    Random rand = new Random();

    public int[] deck = shuffleDeck(); //public scope, one instance shared by all methods

    public static void main(String[] args) {

        //simple arrays to keep track of how many of each card in hand. Facevalue -1

        int[] playerHand = new int[13];
        int[] playerBooks = new int[13];
        int[] cpuHand = new int[13];
        int[] cpuBooks = new int[13];
        //TODO make a data structure which better represents a hand of cards.

        //Deal out seven cards to each player
        System.out.println("Dealing cards...");
        for (i = 0; i < 7; i++) {
            int newCard = dealACard();
            playerHand[newCard]++;
            newCard = dealACard();
            cpuHand[newCard]++;
        }


        Boolean endGame = false; //sentinel
        int cpuAsk = 0; //for rudimentary AI

        //Main game loop
        while (!endGame) {
            //Run a turn

            System.out.println("Your cards: A 2 3 4 5 6 7 8 9 J Q K");
            System.out.printf("            %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d", showCards(playerHand));
            System.out.printf("     BOOKS: %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d", showCards(playerBooks));
            System.out.println();

            //For debugging, obviously
            System.out.println("  My cards: A 2 3 4 5 6 7 8 9 J Q K");
            System.out.printf("            %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d", showCards(cpuHand));
            System.out.printf("     BOOKS: %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d %1d", showCards(cpuBooks));
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
            //otherwise, Fish a new card with dealACard()
            //if fished card is seekCard, then player gets another turn
            //TODO wrap player turn in its own while loop for bonus turns
            //check player hand to see if player has four of any card
            //if so, move those cards into player book

            //TODO CPU turn
            //Select a seek card (initial algo: increment cpuAsk by 1 each turn)
            //Print a message about card asking
            //Automatically seek through player hand
            //if match, remove all from player hand and add to cpu hand
            //otherwise, fish a new card with dealACard()
            //if fished card is seekCard, then cpu gets another turn
            //TODO wrap cpu turn in its own while loop for bonus turns
            //check cpu hand to see if cpu has four of any card
            //if so, move those cards into cpu book


            endGame = true;
            for (i = 0; i < 13; i++) {
                if (!(playerBooks[i] == 4 || cpuBooks[i] == 4)) {
                    endGame = false; //keep playing until all books are made
                }
            }
        }
        int playerScore, cpuScore = 0;
        for (i = 0; i < 13; i++) {
            if (playerBooks[i] == 4) playerScore++;
            if (cpuBooks[i] == 4) cpuScore++;
        }
        System.out.println();

        // Close scanners. Good practice to clean up resources you use.
        // Don't try to use scanners after this point. All code that uses scanners goes above here.
        stringScanner.close();
        numberScanner.close();
    }

    private static int[] shuffleDeck() {
        int[] worldCards = new int[13]; //TODO kludge to keep lame RNG from making more than 4 cards.
        for (int i = 0; i < 13; i++) {
            worldCards[i] = 4; //counter of how many of each card left in deck
        }import com.sun.org.apache.xpath.internal.operations.Bool;
        return worldCards;
    }

    private static int dealACard() {
        //TODO should actually handle logic of drawing random cards one by one
        //For now, we will just generate a random number between 0 and 12.

        //0 = ace
        //1 = 2
        //9 = 10
        //10 = jack
        //11 = queen
        //12 = king


        Boolean legitCard = false;
        while (!legitCard) {
            int cardVal = rand.nextInt(13);
            if (deck[cardVal] > 0) {
                deck[cardVal]--;
                legitCard = true;
            }
            //otherwise, keep randomizing
        }

        return cardVal;
    }

    private static Object[] showCards(int[] cardInts){
        //apparently, we can't pass an array of ints into string formatter
        //here, we convert them into something the string formatter accepts
        //http://stackoverflow.com/questions/5606338/cast-primitive-type-array-into-object-array-in-java
        Object[] cardObjs = new Object[Array.getLength(cardInts)];
        for (int i = 0; i < Array.getLength(cardInts); ++i){
            cardObjs[i] = Array.get(cardInts, i);
        }
        return cardObjs;
    }
}