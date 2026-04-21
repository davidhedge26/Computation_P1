package p3Files.tm;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author David Hedge, Jared Guidry
 */
public class TM {
    private TMState head;
    private TMState tail;
    private File file;
    private char[] machine;
    private char[][][] transitions;

    /**
     * Constructor for Turing Machine class
     */
    public TM() {
        head = new TMState();
        tail = new TMState();
    }

    /**
     * Constructor for Turing Machine class that is given a file to use
     */
    public TM(File given) {
        file = given;
        head = new TMState();
        tail = new TMState();
        parse(given);
    }

    /**
     * parses the inputed file and builds the Turing Machine off of that
     * @param file
     * @return true if run correctly, false if not
     */
    public boolean parse(File file) {
        Scanner scn = new Scanner(file);
        int lines = Integer.parseInt(scn.nextLine());
        machine = new char[lines+1];
        // make a number of states equal to the required amount given by the file
        for (int n = 0; n < lines; n++){
            machine[n] = new Character(n);
        }

        // make a number of transitions equal to the amount required by the given file
        for (){

        }

        // add transitions to every state from every state on every transition
        // this is communicated by the given file in 1,1,R format
        // start at state 0 on trans 0 to state n on trans m
        for () {
            for () {

            }
        }
    }

    /**
     * Returns the state the given transition will lead to
     * @param trans
     * @return TMState a transition will lead to, null if nothing
     */
    public TMState nextState(char trans) {
        if (trans != 'R' && trans != 'L'){
            return null;
        }
        
        TMState retval = null;
        return retval;
    }

    /**
     * Write a symbol into the cell. 
     * Function exists for checking if a string works in maneuvering through the Turing Machine
     * @param toWrite
     * @param pos
     * @return true if function worked properly, false if not
     */
    public boolean writeSymbol(char toWrite, int pos) {
        if (pos < 0 || pos-1 > machine.length) return false;
        machine[pos] = toWrite;
        return true;
    }

    /**
     * Moves the iterator to the next state in the machine according to a given L or R instruction
     * @param trans
     * @return true if successfully moved, false if nextState is null or instruction is bad
     */
    public boolean move(char trans) {
        if (trans != 'R' && trans != 'L'){
            return false;
        } else if (nextState(trans) == null){
            return false;
        }
        return true;
    }

    /**
     * parses a string to see if it successfully reaches the end of a turing machine
     * @return true if successful, false if end of string reached with no end state reached
     */
    public boolean accepts(){
        return false;
    }

    /**
     * toString function for TM
     * @return a String representation of the information held inside the Turing Machine
     */
    @Override
    public String toString(){
        String retString = "";
        return retString;
    }

}
