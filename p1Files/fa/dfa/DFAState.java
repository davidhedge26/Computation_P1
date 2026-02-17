package fa.dfa;

import fa.State;

/**
 * DFAState object to represent state
 * 
 * @author David H, Jared G
 */

public class DFAState extends State {

    private Character[] transitions = new Character[2];
    private int tail;

    DFAState(String name) {
        super(name);
        tail = 0;
    }

    public boolean containsTran(char query) {
        if (transitions[0] == null){
            return false; // list hasn't begun being filled yet. List does not contain transition
        }
        for (int n = 0; n < transitions.length-1; n++){
            if (transitions[n].equals(query)){
                return true;
            }
            if (transitions[n] == null){
                return false;
            }
        }
        return false;
    }

    public boolean addTransition(char newTran){
        if (tail+1 > transitions.length){
            overflow();
        }
        transitions[tail] = newTran;
        tail++;

        return true;
    }


    private void overflow(){
        Character[] bigger = new Character[transitions.length*2];
        for (int n = 0; n < transitions.length*2; n++){
            bigger[n] = transitions[n];
        }
        transitions = bigger;
    }
}
