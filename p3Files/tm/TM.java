package tm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author David Hedge, Jared Guidry
 */
public class TM {

    private char[][][] transitions;

    /**
     * Constructor for Turing Machine class
     */
    public TM() {

    }

    /**
     * Constructor for Turing Machine class that is given a file to use
     */
    public TM(String given) {
        try {
            parse(given);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * parses the inputed file and builds the Turing Machine off of that
     * builds the necessary elements of a DFA
     * 
     * @param file
     * @return true if run correctly, false if not
     */
    public void parse(String path) throws FileNotFoundException {

        try {
            Scanner scn = new Scanner(new File(path));
            int states = Integer.parseInt(scn.nextLine());
            int symbol = Integer.parseInt(scn.nextLine());
            // make a number of states equal to the required amount given by the file
            // each transition [from_state],[on_symbol],[toState,Write,move(0 or 1)]
            this.transitions = new char[states][symbol + 1][3];

            for (int i = 0; i < states; i++) {

                for (int j = 0; j <= symbol; j++) {
                    if (scn.hasNextLine()) {

                        String[] parts = scn.nextLine().split(",");

                        // Store it in the coordinates provided by the file
                        this.transitions[i][j][0] = (char) Integer.parseInt(parts[0]);

                        this.transitions[i][j][1] = (char) Integer.parseInt(parts[1]);

                        this.transitions[i][j][2] = parts[2].charAt(0);

                    }
                    System.out.print("From State: " + i + " ");
                    System.out.print("On Symbol: " + j + " ");
                    System.out.print("To state: " + (int) transitions[i][j][0] + " ");
                    System.out.print("Write Symbol: " + (int) transitions[i][j][1] + " ");
                    System.out.println("Move: " + transitions[i][j][2] + "\n");
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    /**
     * Returns the state the given transition will lead to
     * 
     * @param trans
     * @return TMState a transition will lead to, null if nothing
     */
    // public TMState nextState(char trans) {
    // if (trans != 'R' && trans != 'L') {
    // return null;
    // }

    // TMState retval = null;
    // return retval;
    // }

    /**
     * Write a symbol into the cell.
     * Function exists for checking if a string works in maneuvering through the
     * Turing tape
     * 
     * @param toWrite
     * @param pos
     * @return true if function worked properly, false if not
     */
    // public boolean writeSymbol(char toWrite, int pos) {
    // if (pos < 0 || pos - 1 > tape.length)
    // return false;
    // tape[pos] = toWrite;
    // return true;
    // }

    // /**
    // * Moves the iterator to the next state in the tape according to a given L or
    // R instruction
    // * @param trans
    // * @return true if successfully moved, false if nextState is null or
    // instruction is bad
    // */
    // public boolean move(char trans) {
    // if (trans != 'R' && trans != 'L'){
    // return false;
    // } else if (nextState(trans) == null){
    // return false;
    // }
    // return true;
    // }

    /**
     * parses a string to see if it successfully reaches the end of a turing machine
     * 
     * @return true if successful, false if end of string reached with no end state
     *         reached
     */
    // public boolean accepts() {
    // return false;
    // }

    /**
     * toString function for TM
     * 
     * @return a String representation of the information held inside the Turing
     *         Machine
     */
    // @Override
    // public String toString() {
    // String retString = "";
    // return retString;
    // }

    public static void main(String[] args) {
        TM machine = new TM(args[0]);

    }

}
