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

    DFAState(String name) {
        super(name);
        tail = 0;
    }

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

    public boolean addTransition(String toState, char newTran){
        if (tail+1 > transitions.length){
            overflow();
        }
        transitions[tail] = newTran;
        destinations[tail] = toState;
        tail++;

        return true;
    }

    public String next(char tran){
        for (int n = 0; n < transitions.length; n++){
            if (transitions[n] == tran){
                return destinations[n];
            }
        }
        return null;
    }


    private void overflow(){
        Character[] bigger = new Character[transitions.length*2];
        for (int n = 0; n < transitions.length*2; n++){
            bigger[n] = transitions[n];
        }
        transitions = bigger;
    }
}
