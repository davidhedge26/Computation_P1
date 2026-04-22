package fa.dfa;

import java.util.LinkedHashSet;
import java.util.Map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * DFA models and manages Definite Finite Automata
 * 
 * @author David H, Jared G
 */
public class DFA implements DFAInterface {

    /**
     * 5 tuple:
     * sigma is the alphabet
     * states are the dfa state objects that currently just hold a name for a state
     * init is just 1 singular dfa state
     * final state is set as there could be multiple
     * using Linked Hash versions of sets because these preserve order
     * inner map uses character as key, dfa rules
     * outter map results in end transition to next state
     */
    private LinkedHashSet<Character> sigma = new LinkedHashSet<>();
    private LinkedHashSet<DFAState> states = new LinkedHashSet<>();
    private DFAState init = new DFAState(null);
    private LinkedHashSet<DFAState> finState = new LinkedHashSet<>();
    private LinkedHashMap<DFAState, HashMap<Character, DFAState>> transitionState = new LinkedHashMap<>();

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

                // Does the character reference a valid transition?
                if (!iter.containsTran(x)) {
                    return false;
                }

                // Have we reached the end of the string? Does the string actually go to a valid
                // state?
                iter = getState(iter.next(x));
                if ((iter != null) && (n == s.length() - 1)) {
                    // Is the last state reached a valid final state?
                    return isFinal(iter.getName());
                }
            } catch (IndexOutOfBoundsException e) {
                // For when the string never reaches a final state and runs out of characters
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
                    return true;

                // add transition to fromState
                check.addTransition(toState, onSymb);
                // check if entry created in map, create new, else add transition to it:
                DFAState from = getState(fromState);
                DFAState to = getState(toState);
                if (!transitionState.containsKey(from)) {
                    transitionState.put(from, new HashMap<>());
                    transitionState.get(from).put(onSymb, to);
                } else {
                    transitionState.get(from).put(onSymb, to);
                }
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
    public DFA swap(char symb1, char symb2) {
        // Instantiate a new DFA with given sigmas
        DFA copy = new DFA();
        copy.addSigma(symb1);
        copy.addSigma(symb2);

        Iterator<DFAState> iter = states.iterator();
        while (iter.hasNext()) {
            // Grab next state and make it initial or final in copy if it is so in the
            // original DFA
            DFAState next = iter.next();
            String name = next.getName();
            copy.addState(name);
            if (finState.contains(next)) {
                copy.setFinal(name);
            }
            if (isStart(name)) {
                copy.setStart(name);
            }
        }
        // Iterate through all states again to add swapped transitions
        iter = states.iterator();
        while (iter.hasNext()) {
            DFAState next = iter.next();
            String name = next.getName();
            // Add reversed transitions into states
            if (next.containsTran(symb1)) {
                copy.addTransition(name, next.next(symb1), symb2);
            }
            if (next.containsTran(symb2)) {
                copy.addTransition(name, next.next(symb2), symb1);
            }
        }

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
        sb.append("}\n");
        // Sigma Alphabet To String
        sb.append("Sigma = {");
        for (Character iter : sigma) {
            sb.append(iter.toString() + " ");
        }
        sb.append("}\n");
        // Delta To String
        sb.append("delta =\n");
        sb.append(deltaString());

        // q0 to String
        sb.append("q0 = ").append(init.toString() + "\n");
        // accept states
        sb.append("F = { ");
        for (DFAState iter : finState) {
            sb.append(iter.toString() + " ");
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * helper function to toString the delta structure
     * needs to return the transition list
     * in the order of the states
     * needs to print the results in the order of the alphabet
     * 
     * @param none
     * @return Delta Structure String
     * 
     * 
     */

    public String deltaString() {
        // sb will be our alphabet title
        // sl will be the state transition printout
        // two string builders, one map iteration
        StringBuilder sb = new StringBuilder();
        StringBuilder sl = new StringBuilder();
        // append alphabet as header
        for (Character alphabet : sigma) {
            sb.append(" " + alphabet);
        }
        sb.append("\n");
        // need to retrieve table items, in order of the states
        // loop through states
        // append from transition table
        for (DFAState temp : states) {
            for (Map.Entry<DFAState, HashMap<Character, DFAState>> entry : transitionState.entrySet()) {
                if (entry.getKey() == temp) {
                    sl.append(" " + temp.toString());
                    HashMap<Character, DFAState> innerMap = entry.getValue();
                    for (Character bet : sigma) {
                        for (HashMap.Entry<Character, DFAState> iter : innerMap.entrySet()) {
                            if (iter.getKey() == bet)
                                sl.append(" " + iter.getValue());
                        }
                    }
                    sl.append("\n");
                }

            }
        }
        sb.append(sl.toString());
        return sb.toString();
    }

}
