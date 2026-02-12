package fa.dfa;

import java.util.LinkedHashSet;
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
    private LinkedHashMap<Character, DFAState> innerdelta = new LinkedHashMap<>();
    private LinkedHashMap<LinkedHashMap<Character, DFAState>, DFAState> outterdelta = new LinkedHashMap<>();

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStart'");
    }

    /**
     * Adds a symbol to Sigma
     * 
     * @param symbol to add to the alphabet set
     */
    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
        sigma.contains(symbol); // This line is simply for display purposes, not for functional reasons
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addSigma'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accepts'");
    }

    /**
     * Getter for Sigma
     * 
     * @return the alphabet of FA
     */
    @Override
    public Set<Character> getSigma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSigma'");
    }

    /**
     * Returns state with the given name, or null if none exists
     * 
     * @param name of a state
     * @return state object or null
     */
    @Override
    public State getState(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getState'");
    }

    /**
     * Determines if a state with a given name is final
     * 
     * @param name the name of the state
     * @return true if a state with that name exists and it is final
     */
    @Override
    public boolean isFinal(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isFinal'");
    }

    /**
     * Determines if a state with name is final
     * 
     * @param name the name of the state
     * @return true if a state with that name exists and it is the start state
     */
    @Override
    public boolean isStart(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isStart'");
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
        // Find the fromState in the list
        Iterator<DFAState> iter = states.iterator();
        while (iter.hasNext()){
            DFAState check = iter.next();
            if (check.getName().equals(fromState)){
                // add transition to fromState
                check.addTransition(onSymb);
            }
        }
        return true;
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
    public DFA swap(char symb1, char symb2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'swap'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'swap'");
    }
}
