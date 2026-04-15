package p3Files.tm;

import java.util.LinkedList;

/**
 * @author David Hedge, Jared Guidry
 */
public class TM {
    private LinkedList machine;
    private TMState head;
    private TMState tail;

    /**
     * Constructor for Turing Machine class
     */
    public TM() {
        machine = new LinkedList<>();
        head = new TMState();
        tail = new TMState();
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
     * @return true if function worked properly, false if not
     */
    public boolean writeSymbol(char toWrite) {
        
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
