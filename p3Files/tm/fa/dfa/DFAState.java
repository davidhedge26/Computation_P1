package fa.dfa;

import fa.State;

/**
 * DFAState object to represent state
 * 
 * @author David H, Jared G
 */

public class DFAState extends State {

    private Character[] transitions = new Character[2];
    private String[] destinations = new String[2]; // Destination should be held at the same position as the transition that leads to it
    private int tail;

    /**
     * Constructor for DFAStates
     * @param name
     */
    DFAState(String name) {
        super(name);
        tail = 0;
    }

    /**
     * Tells if a query transition exists in this DFAState
     * @param query
     * @return true if state can use query transition, false if not
     */
    public boolean containsTran(char query) {
        if (transitions[0] == null){
            return false; // list hasn't begun being filled yet. List does not contain transition
        }
        for (int n = 0; n < transitions.length; n++){
            if (transitions[n] == null){
                return false;
            }
            if (transitions[n].equals(query)){
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a transition to this DFAState. 
     * Stores the destination of that transition at the same location in a different array
     * if transitions[1] = '1' then destination[1] = the location transitions[1] wants to go
     * @param toState
     * @param newTran
     * @return true when performed correctly
     */
    public boolean addTransition(String toState, char newTran){
        if (tail+1 > transitions.length){
            overflow();
        }
        transitions[tail] = newTran;
        destinations[tail] = toState;
        tail++;

        return true;
    }

    /**
     * Gives the name of the next DFAState according to the given transition
     * @param tran
     * @return the name of the next DFAState. Null if there is no next DFAState.
     */
    public String next(char tran){
        for (int n = 0; n < transitions.length; n++){
            if (transitions[n] == tran){
                return destinations[n];
            }
        }
        return null;
    }

    /**
     * Makes a new transitions[] & destinations[] array twice the size of the old arrays
     */
    private void overflow(){
        Character[] biggerT = new Character[transitions.length*2];
        String[] biggerD = new String[destinations.length*2];
        for (int n = 0; n < transitions.length*2; n++){
            biggerT[n] = transitions[n];
            biggerD[n] = destinations[n];
        }
        transitions = biggerT;
        destinations = biggerD;
    }
}
