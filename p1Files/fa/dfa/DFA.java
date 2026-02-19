package fa.dfa;

import java.util.LinkedHashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import fa.State;

/**
 * DFA models and manages Definite Finite Automata
 * 
 * @author David H, Jared G
 */
public class DFA implements DFAInterface {

    // 5 tuple:
    // sigma is the alphabet
    // states are the dfa state objects that currently just hold a name for a state
    // init is just 1 singular dfa state
    // final state is set as there could be multiple
    // using Linked Hash versions of sets because these preserve order
    // inner map uses character as key, dfa rules
    // outter map results in end transition to next state
    private LinkedHashSet<Character> sigma = new LinkedHashSet<>();
    private LinkedHashSet<DFAState> states = new LinkedHashSet<>();
    private DFAState init = new DFAState(null);
    private LinkedHashSet<DFAState> finState = new LinkedHashSet<>();
    private HashMap<DFAState, Character> stateTransition = new LinkedHashMap<>();
    private LinkedHashMap<HashMap<DFAState, Character>, DFAState> transitionState = new LinkedHashMap<>();

    /**
     * Adds a a state to the FA instance
     * 
     * @param name is the label of the state
     * @return true if a new state created successfully and false if there is
     *         already state with such name
     */
    @Override
    public boolean addState(String name) {

        for (DFAState checker : this.states) {
            if (checker.toString().equalsIgnoreCase(name))
                return false;
        }
        this.states.add(new DFAState(name));
        return true;
    }

    /**
     * Marks an existing state as an accepting state
     * 
     * @param name is the label of the state
     * @return true if successful and false if no state with such name exists
     */
    @Override
    public boolean setFinal(String name) {

        for (DFAState checker : this.states) {
            if (checker.toString().equalsIgnoreCase(name)) {
                this.finState.add(checker);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the initial state to the DFA instance
     * 
     * @param name is the label of the start state
     * @return true if successful and false if no state with such name exists
     */
    @Override
    public boolean setStart(String name) {
        for (DFAState iter : states) {
            if (iter.toString().equalsIgnoreCase(name)) {
                init = iter;
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a symbol to Sigma
     * 
     * @param symbol to add to the alphabet set
     */
    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    /**
     * Simulates a DFA on input s to determine
     * whether the DFA accepts s.
     * 
     * @param s - the input string
     * @return true if s in the language of the DFA and false otherwise
     */
    @Override
    public boolean accepts(String s) {
        DFAState iter = init;
        boolean fin = true;
        for (int n = 0; fin == true; n++) {
            try {
                char x = s.charAt(n);

                // Is the character valid?
                if (iter.containsTran(x)){
                    iter = getState(iter.next(x));
                    if ((iter != null) && (n == s.length()-1)){
                        return isFinal(iter.getName());
                    }
                } else {
                    fin = false;
                }
            } catch (IndexOutOfBoundsException e){
                return false;
            }
        }
        return false;
    }

    /**
     * Getter for Sigma
     * 
     * @return the alphabet of FA
     */
    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    /**
     * Returns state with the given name, or null if none exists
     * 
     * @param name of a state
     * @return state object or null
     */
    @Override
    public DFAState getState(String name) {
        for (DFAState iter : states) {
            if (iter.toString().equalsIgnoreCase(name))
                return iter;
        }
        return null;
    }

    /**
     * Determines if a state with a given name is final
     * 
     * @param name the name of the state
     * @return true if a state with that name exists and it is final
     */
    @Override
    public boolean isFinal(String name) {
        return (finState.contains(getState(name)));
    }

    /**
     * Determines if a state with name is start
     * 
     * @param name the name of the state
     * @return true if a state with that name exists and it is the start state
     */
    @Override
    public boolean isStart(String name) {
        return (name == init.getName());
    }

    /**
     * Adds the transition to the DFA's delta data structure
     * 
     * @param fromState is the label of the state where the transition starts
     * @param toState   is the label of the state where the transition ends
     * @param onSymb    is the symbol from the DFA's alphabet.
     * @return true if successful and false if one of the states don't exist or the
     *         symbol in not in the alphabet
     */
    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        // check if transition exists in alphabet
        if (!sigma.contains(onSymb))
            return false;

        if (getState(toState) == null)
            return false;

        // This chunk can be removed with just "if (getSate(toState) == null) return
        // false;"
        // This looks to be faster though, just barely. Even still I don't think
        // it's worth removing
        // Find the fromState in the list
        Iterator<DFAState> iter = states.iterator();
        while (iter.hasNext()) {
            DFAState check = iter.next();
            if (check.getName().equals(fromState)) {
                // if the transition already exists in that object then return false
                if (check.containsTran(onSymb))
                    return false;

                // add transition to fromState
                check.addTransition(toState, onSymb);
                this.stateTransition.put(check, onSymb);
                this.transitionState.put(this.stateTransition, getState(toState));
                return true;
            }
        }
        // Runs only when no state is found with the same name as given fromState
        return false;
    }

    /**
     * Creates a deep copy of this DFA
     * which transitions labels are
     * swapped between symbols symb1
     * and symb2.
     * 
     * @return a copy of this DFA
     */
    @Override
    public DFA swap(char symb2, char symb1) {
        DFA copy = new DFA();

        Iterator<DFAState> iter = states.iterator();
        while (iter.hasNext()) {
            DFAState next = iter.next();
            copy.addState(next.getName());
            if (finState.contains(next)) {
                copy.setFinal(next.getName());
            }
            if (isStart(next.getName())) {
                copy.init = copy.getState(next.getName());
            }

            copy.getState(next.getName()).addTransition(next.next(symb1), symb2);
            copy.getState(next.getName()).addTransition(next.next(symb2), symb1);
        }
        copy.init = init;

        return copy;
    }

    /**
     * Construct the textual representation of the DFA, for example
     * A simple two state DFA
     * Q = { a b }
     * Sigma = { 0 1 }
     * delta =
     * 0 1
     * a a b
     * b a b
     * q0 = a
     * F = { b }
     * 
     * The order of the states and the alphabet is the order
     * in which they were instantiated in the DFA.
     * 
     * @return String representation of the DFA
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Q States To String
        sb.append("Q = { ");

        for (DFAState iter : states) {
            sb.append(iter.toString() + " ");
        }
        sb.append("}");
        // Sigma Alphabet To String
        sb.append("\nSigma = { ");
        for (Character iter : sigma) {
            sb.append(iter.toString() + " ");
        }
        sb.append("}");
        // Delta To String
        sb.append("\ndelta = \n");
        for (HashMap.Entry<DFAState, Character> set : stateTransition.entrySet()) {
            sb.append(set.getValue());
        }
        for (Map.Entry<HashMap<DFAState, Character>, DFAState> set : transitionState.entrySet()) {
            sb.append("\n" + set.getKey()).append(set.getValue());
        }
        // q0 to String
        sb.append("\n q0 = ").append(init.toString());
        // accept states
        sb.append("\n F = { ");
        for (DFAState iter : finState) {
            sb.append(iter.toString() + " ");
        }
        sb.append("}");
        return sb.toString();
    }

}
