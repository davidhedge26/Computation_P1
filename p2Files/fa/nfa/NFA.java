package fa.nfa;

public class NFA implements NFAinterface {

    /**
     * new methods from interface
     * (toward bottom):"isDFA","maxcopies","eClosure","getToState"
     *
     * Carried over code from previous, large sticking point is transitions
     * (in state obj, or use map), but not both
     * currently utilizing map
     * 
     * 
     * must reserve "e" for epsilon
     * 
     * 5 tuple:
     * sigma is the alphabet
     * states are the NFA state objects that currently just hold a name for a state
     * init is just 1 singular NFA state
     * final state is set as there could be multiple
     * using Linked Hash versions of sets because these preserve order
     * inner map uses char as key, NFA as value
     * outter map from state as key, inner map as value
     * a key can have multiple value rows in associated value map
     * 
     * NFA: has e-closure
     */
    private LinkedHashSet<Character> sigma = new LinkedHashSet<>();
    private LinkedHashSet<NFAState> states = new LinkedHashSet<>();
    private NFAState init = new NFAState(null);
    private LinkedHashSet<NFAState> finState = new LinkedHashSet<>();
    private LinkedHashMap<NFAState, HashMap<Character, NFAState>> transitionState = new LinkedHashMap<>();

    /**
     * Adds a a state to the NFA instance
     * 
     * @param name is the label of the state
     * @return true if a new state created successfully and false if there is
     *         already state with such name
     */
    @Override
    public boolean addState(String name) {

        for (NFAState checker : this.states) {
            if (checker.toString().equalsIgnoreCase(name))
                return false;
        }
        this.states.add(new NFAState(name));
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

        for (NFAState checker : this.states) {
            if (checker.toString().equalsIgnoreCase(name)) {
                this.finState.add(checker);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the initial state to the NFA instance
     * 
     * @param name is the label of the start state
     * @return true if successful and false if no state with such name exists
     */
    @Override
    public boolean setStart(String name) {
        for (NFAState iter : states) {
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
    public NFAState getState(String name) {
        for (NFAState iter : states) {
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
     * Adds the transition to the NFA's delta data structure
     * Utilizing the map structure, rather than the State held transitions
     * 
     * @param fromState is the label of the state where the transition starts
     * @param toState   is the label of the state where the transition ends
     * @param onSymb    is the symbol from the NFA's alphabet.
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

        Iterator<NFAState> iter = states.iterator();
        while (iter.hasNext()) {
            NFAState check = iter.next();
            if (check.getName().equals(fromState)) {
                // check if entry created in map, create new, else add transition to it:
                NFAState from = getState(fromState);
                NFAState to = getState(toState);
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

    @Override
    public NFA swap(char symb1, char symb2) {
        // Instantiate a new NFA with given sigmas
        NFA copy = new NFA();
        copy.addSigma(symb1);
        copy.addSigma(symb2);

        Iterator<NFAState> iter = states.iterator();
        while (iter.hasNext()) {
            // Grab next state and make it initial or final in copy if it is so in the
            // original NFA
            NFAState next = iter.next();
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
            NFAState next = iter.next();
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
     * Construct the textual representation of the NFA, for example
     * A simple two state NFA
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
     * in which they were instantiated in the NFA.
     * 
     * @return String representation of the NFA
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Q States To String
        sb.append("Q = { ");

        for (NFAState iter : states) {
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
        for (NFAState iter : finState) {
            sb.append(iter.toString() + " ");
        }
        sb.append("}");
        return sb.toString();
    }

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
        for (NFAState temp : states) {
            for (Map.Entry<NFAState, HashMap<Character, NFAState>> entry : transitionState.entrySet()) {
                if (entry.getKey() == temp) {
                    sl.append(" " + temp.toString());
                    HashMap<Character, NFAState> innerMap = entry.getValue();
                    for (Character bet : sigma) {
                        for (HashMap.Entry<Character, NFAState> iter : innerMap.entrySet()) {
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

    /**
     * Return delta entries
     * 
     * @param from   - the source state
     * @param onSymb - the label of the transition
     * @return a set of sink states
     */
    public Set<NFAState> getToState(NFAState from, char onSymb) {
    }

    /**
     * Traverses all epsilon transitions and determine
     * what states can be reached from s through e
     * 
     * @param s
     * @return set of states that can be reached from s on epsilon trans.
     */

    public Set<NFAState> eClosure(NFAState s) {

    }

    /**
     * Determines the maximum number of NFA copies
     * created when processing string s
     * 
     * @param s - the input string
     * @return - the maximum number of NFA copies created.
     */
    public int maxCopies(String s) {

    }

    /**
     * Determines if NFA is an instance of a DFA
     * 
     * @return - true if NFA's transition function has DFA's properties.
     */
    public boolean isDFA() {

    }

}
