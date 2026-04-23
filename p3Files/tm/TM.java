package tm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Makes and manages a Turing Machine given the user inputed path to a .txt file containing the blueprint
 * 
 * @author David Hedge, Jared Guidry
 */
public class TM {

    private char[][][] transitions;
    private int[] tape; // an infinite array so there should be no limits on it. Shouldn't make it unnecessarily big either...
    private int[] negTape; // works by starting at -1 and downwards. No value should be stored at 0
    private int finalState;

    /**
     * Constructor for Turing Machine class
     */
    public TM() {
        tape = new int[50];
        negTape = new int[50];
        finalState = '0';
    }

    /**
     * Constructor for Turing Machine class that is given a file to use
     */
    public TM(String given) {
        tape = new int[50];
        negTape = new int[50];
        finalState = '0';
        try {
            parse(given);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * parses the inputed file and builds the Turing Machine off of that
     * 
     * @param file
     */
    public void parse(String path) throws FileNotFoundException {

        try {
            Scanner scn = new Scanner(new File(path));
            int states = Integer.parseInt(scn.nextLine());
            int symbol = Integer.parseInt(scn.nextLine());
            // make a number of states equal to the required amount given by the file
            // each transition [from_state],[on_symbol],[toState,Write,move(0 or 1)]
            this.transitions = new char[states][symbol + 1][3];

            // 'i' is the current state
            for (int i = 0; i < states; i++) {

                // 'j' is the current transition
                for (int j = 0; j <= symbol; j++) {
                    if (scn.hasNextLine()) {

                        String[] parts = scn.nextLine().split(",");

                        // Store it in the coordinates provided by the file
                        this.transitions[i][j][0] = (char) Integer.parseInt(parts[0]);

                        this.transitions[i][j][1] = (char) Integer.parseInt(parts[1]);

                        this.transitions[i][j][2] = parts[2].charAt(0);

                    }

                    // finalState will always be the state last created while parsing. It's fine to overwrite the final state because of this.
                    finalState = i;
                }
            }
            scn.close();
        } catch (Exception e) {
            System.err.println(e);
        }

    }


    /**
     * Runs the turing machine through the tape and outputs the result of that turing machine
     * 
     * @return String result of the turing machine
     */
    public String run() {
        int head = 0;
        int curr = 0;
        int trans = 0;
        int toState = 0;
        int toWrite = 0;
        char move = 'R';

        int maxNeg = 0;
        int maxNorm = 0;

        // Start at state 0 and transition on symbol 0 (tape is empty, this is the only available transition)
        while (curr != finalState){
            toState = (int) transitions[curr][trans][0];
            toWrite = (int) transitions[curr][trans][1];
            move = transitions[curr][trans][2];

            // move to next state in the machine
            curr = toState;

            // write symbol on current slot the head points at
            if (head < 0) negTape[head * -1] = toWrite;
            
            else tape[head] = toWrite;
            

            // move head accordingly
            if (Character.toUpperCase(move) == 'R'){
                if (head > (tape.length - 2)) tape = extend(tape);
                if (head > maxNorm){
                    maxNorm = head;
                }
                
                head++;
            } else { // move is L
                if ((head * -1) > (negTape.length - 2)) negTape = extend(negTape);
                if (head * -1 > maxNeg){
                    maxNeg = head * -1;
                }
                
                head--;
            }

            // Make transition the symbol currently held at the slot the head looks at
            trans = (head < 0) ? negTape[head * -1] : tape[head];
        }

        // Build the string to return
        String retval = "";
        if (negTape.length > 0){
            for (int n = maxNeg+1; n > 0; n--){
                retval += negTape[n] + "";
            }
        }
        for (int n = 0; n < maxNorm+2; n++){
            retval += tape[n] + "";
        }
        return retval;
    }


    /**
     * extend is a helper class purely for the tape array
     * Makes the tape longer for when it reaches its limits
     * 
     * @param given is the array to extend
     * @return the extended and copied array
     */
    private int[] extend(int[] given) {
        int[] newTape = new int[given.length * 2];
        for (int n = 0; n < given.length; n++){
            newTape[n] = given[n];
        }
        return newTape;
        // Java has automatic garbage collection
    }
}
