/* Coursework submission which allows 2 users 
   to play the game 'SubtractASquare' */

import java.util.Scanner;
import java.util.Arrays;

public class SubtractASquare{

    // Variables specified by the game description
    static final int HEAP_SIZE = 13;
    static final int NUM_OF_HEAPS = 3;
    static final int EMPTY_HEAP = 0;

    // Used to track the separate heaps
    static final int HEAP_ONE = 0;
    static final int HEAP_TWO = 1;
    static final int HEAP_THREE = 2;

    // Used to act as a key for players
    static final int PLAYER_ONE = 1;
    static final int PLAYER_TWO = 2;

    // Used to store information about player's skip
    static final int UNUSED_SKIP = 0;
    static final int USING_SKIP = 1;
    static final int USED_SKIP = 2;

    // Used to print out information about the game to the console
    static final String HEAP_INFO_MSG = "Remaining coins: %s\n";
    static final String PLAYER_TURN_MSG = "Player %d: choose a heap: ";
    static final String COINS_CHOICE_MSG = "Now choose a square number of coins: ";
    static final String WINNER_MSG = "Player %d wins!";

    // Error messages
    static final String NON_INT_HEAP_CHOICE_MSG = "Sorry you must enter an integer or skip.";
    static final String NON_INT_COIN_CHOICE_MSG = "Sorry you must enter an integer.";
    static final String ILLEGAL_HEAP_CHOICE_MSG = "Sorry, that's not a legal heap choice.";
    static final String ILLEGAL_COIN_CHOICE_MSG = 
        "Sorry that's not a legal number of coins for that heap.";
    static final String USED_SKIP_MSG = "Sorry you have used your skip.";


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean gameOver = false;
        int playerTurn = PLAYER_ONE;

        // Fills 3 heaps with coins
        int[] heaps = {HEAP_SIZE, HEAP_SIZE, HEAP_SIZE};

        int[] playersSkipped = {UNUSED_SKIP, UNUSED_SKIP}; // Stores if players have skipped
        int playerSkippedIndex = playerTurn - 1; // Minus 1 due to indexing

        // Formats array to readable string
        String heapsString = Arrays.toString(heaps).replace("[", "").replace("]", "");
        System.out.print(String.format(HEAP_INFO_MSG, heapsString)); // Prints remaining coins

        // Initialises variables to be used to store player input data
        String nonIntHeapInput = "";
        int heapChosen = 1;
        int index = 0;
        int numOfCoins = 1;

        // Will be used to determine if player's inputs are valid and game can progress
        boolean validHeapInput;
        boolean validHeapChoice;
        boolean validCoinInput;
        boolean validCoinChoice;


        // Continues until a player wins the game
        while (!gameOver) { 


            // Gets player input for what heap to take coins from
            do {
                validHeapInput = false;
                validHeapChoice = false;


                // Makes sure input is integer or "skip"
                do {
                    System.out.print(String.format(PLAYER_TURN_MSG, playerTurn));
                    
                    if (!sc.hasNextInt()) {
                        nonIntHeapInput = sc.nextLine();

                        if (nonIntHeapInput.equalsIgnoreCase("skip")) {
                                
                            // Checks if player has already used their skip
                            if (playersSkipped[playerSkippedIndex] == USED_SKIP) {
                                System.out.println(USED_SKIP_MSG);

                            } else {
                                // Sets the skip variable to true
                                playersSkipped[playerSkippedIndex] = USING_SKIP;
                                validHeapInput = true;
                            }

                        } else {
                            // Input is invalid, prints error message
                            System.out.println(NON_INT_HEAP_CHOICE_MSG);
                        }

                    } else {
                        // Integer input
                        validHeapInput = true;
                    }
                } 
                while (!validHeapInput);


                // Checks to see if player did not use their skip
                if (playersSkipped[playerSkippedIndex] != USING_SKIP) {

                    heapChosen = sc.nextInt();
                    sc.nextLine();

                    // Gets the index of which heap should be accessed
                    index = heapChosen - 1;
                
                    // Checks to see if chosen heap exists and is not empty
                    validHeapChoice = 0 < heapChosen && heapChosen <= NUM_OF_HEAPS 
                    && EMPTY_HEAP < heaps[index];

                    if (!validHeapChoice) {
                        System.out.println(ILLEGAL_HEAP_CHOICE_MSG);
                    }

                } else {
                    // Player used skip
                    validHeapChoice = true;
                }
            }
            /* Repeats asking for player input until player enters valid heap number: 
            value of 1, 2 or 3 and heap not empty, and player hasn't skipped */
            while (!validHeapChoice);

            
            // Checks that player isn't currently using their skip
            if (playersSkipped[playerSkippedIndex] != USING_SKIP) {


                // Gets how many coins player want to take from the heap
                do {
                    validCoinInput = false;
                    validCoinChoice = false;


                    // Checks to make sure that number of coins entered is an integer
                    do {
                        System.out.print(COINS_CHOICE_MSG);

                        if (!sc.hasNextInt()) {
                            
                            // Non integer input so prints error message
                            System.out.println(NON_INT_COIN_CHOICE_MSG);
                            sc.nextLine();

                        } else {
                            // Integer input
                            validCoinInput = true;
                        }
                    }
                    while (!validCoinInput);


                    numOfCoins = sc.nextInt();
                    sc.nextLine();

                    /* Checks to see if chosen coins are a square number
                       and do not surpass coins in heap */
                    double sqrt = Math.sqrt(numOfCoins);
                    validCoinChoice = sqrt - Math.floor(sqrt) == 0
                                      && numOfCoins <= heaps[index];

                    if (!validCoinChoice) {
                        System.out.println(ILLEGAL_COIN_CHOICE_MSG);

                    } else {
                        // Subtracts coins from the chosen heap
                        heaps[index] -= numOfCoins;
                    }
                }
                /*  Repeats asking for player input until player enters valid number of coins 
                     and number of coins doesn't exceed coins in chosen heap */
                while (!validCoinChoice);


                if (heaps[HEAP_ONE] == EMPTY_HEAP && heaps[HEAP_TWO] == EMPTY_HEAP 
                && heaps[HEAP_THREE] == EMPTY_HEAP) {

                    // Ends loop
                    System.out.println(String.format(WINNER_MSG, playerTurn));
                    gameOver = true;

                } else {
                    // Formats array to readable string
                    heapsString = Arrays.toString(heaps).replace("[", "").replace("]", "");
                    System.out.print(String.format(HEAP_INFO_MSG, heapsString)); // Prints coins
                }

            } else {
                // Player is using skip so gets rid of player's abilty to skip again
                playersSkipped[playerSkippedIndex] = USED_SKIP;
            }


            // Current player is switched
            if (playerTurn == PLAYER_ONE) {
                playerTurn = PLAYER_TWO;
                
            } else {
                playerTurn = PLAYER_ONE;
            }
            // Points to the correct skip variable for next player
            playerSkippedIndex = playerTurn - 1;
        }

        sc.close();
    }
}
