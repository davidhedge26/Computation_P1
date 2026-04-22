package tm;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
import fa.dfa.*;

/**
 * @author David Hedge, Jared Guidry
 */
public class TM {
    private TMState head;
    private TMState tail;
    private File file;
    private char[] tape;
    private char[][][] transitions;
    private DFA dfa;

    /**
     * Constructor for Turing Machine class
     */
    public TM() {
        dfa = new DFA();
        head = new TMState();
        tail = new TMState();
        tape = new char[100];
    }

    /**
     * Constructor for Turing Machine class that is given a file to use
     */
    public TM(File given) {
        dfa = new DFA();
        file = given;
        head = new TMState();
        tail = new TMState();
        tape = new char[100];
        parse(given);
    }

    /**
     * parses the inputed file and builds the Turing Machine off of that
     * builds the necessary elements of a DFA
     * 
     * @param file
     * @return true if run correctly, false if not
     */
    public boolean parse(File file) {
        Scanner scn = new Scanner(file);
        int states = Integer.parseInt(scn.nextLine());
        int symbol = Integer.parseInt(scn.nextLine());
        // make a number of states equal to the required amount given by the file
        // each transition [from_state],[on_symbol],[]
        transitions = new char[states][symbol][3];

        // make a number of transitions equal to the amount required by the given file
        int numTrans = Integer.parseInt(scn.nextLine()) + 1; // add 1 for the 0th transition
        for (int n = 0; n < numTrans; n++) {
            dfa.addSigma((char) n);
        }

        // add transitions to every state from every state on every transition
        // this is communicated by the given file in 1,1,R format
        // start at state 0 on trans 0 to state n on trans m
        for (int n = 0; scn.hasNextLine(); n++) {
            String curr = scn.nextLine();
            for (int i = 0; i < numTrans; i++) {
                dfa.addTransition(n + "", i + "", curr.charAt(0));
            }
        }
    }

    /**
     * Returns the state the given transition will lead to
     * 
     * @param trans
     * @return TMState a transition will lead to, null if nothing
     */
    public TMState nextState(char trans) {
        if (trans != 'R' && trans != 'L') {
            return null;
        }

        TMState retval = null;
        return retval;
    }

    /**
     * Write a symbol into the cell.
     * Function exists for checking if a string works in maneuvering through the
     * Turing tape
     * 
     * @param toWrite
     * @param pos
     * @return true if function worked properly, false if not
     */
    public boolean writeSymbol(char toWrite, int pos) {
        if (pos < 0 || pos - 1 > tape.length)
            return false;
        tape[pos] = toWrite;
        return true;
    }

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
    public boolean accepts() {
        return false;
    }

    /**
     * toString function for TM
     * 
     * @return a String representation of the information held inside the Turing
     *         Machine
     */
    @Override
    public String toString() {
        String retString = "";
        return retString;
    }

}
