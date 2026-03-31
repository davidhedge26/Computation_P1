package fa.nfa;

import java.util.LinkedHashSet;
import java.util.Map;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Stack;
import java.lang.Math;

import fa.nfa.NFAState;

/**
 * NFA.java creates and handles all the functions necessary for modeling an NFA.
 * By inputing states, transitions, and which states on what transitions lead to which 
 * states traversing the modeled NFA is easy.
 * 
 * @author David Hedge, Jared Guidry
 */
public class NFA implements NFAInterface {

    /**
     * new methods from interface
     * (toward bottom):"isNFA","maxcopies","eClosure","getToState"
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
    private LinkedHashSet<Character> sigma;
    private LinkedHashSet<NFAState> states;
    private NFAState init = new NFAState(null);
    private LinkedHashSet<NFAState> finState;
    private LinkedHashMap<NFAState, HashMap<Character, Set<NFAState>>> transitionState;
    // private int copies;


    /**
     * Constructor method for the NFA class
     */
    public NFA(){
        this.sigma = new LinkedHashSet<>();
        this.states = new LinkedHashSet<>();
        this.init = new NFAState(null);
        this.finState = new LinkedHashSet<>();
        this.transitionState = new LinkedHashMap<>();
        // this.copies = 0;
        this.sigma.add('e');
    }

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
     * Recursive helper function for accepts
     * @param arr - character array of remaining input string
     * @return Set<NFAState> of states the string reaches
     */
    private Set<NFAState> acceptsRecursive(char[] arr, NFAState curr, int[] copies) {
        // Get full set of states reachable from curr via epsilon
        Set<NFAState> currClosure = new HashSet<>();
        currClosure.add(curr);
        currClosure.addAll(eClosure(curr));

        // Max copies calculated
        copies[0] = Math.max(copies[0], currClosure.size());

        // Base case: String has been run through and is now empty
        if (arr.length == 0) {
            // Max copies calculated (again)
            copies[0] = Math.max(copies[0], currClosure.size());
            return currClosure;
        }

        // Go to the next in the given String
        char[] newArr = new char[arr.length - 1];
        for (int n = 1; n < arr.length; n++) {
            newArr[n - 1] = arr[n];
        }

        // From every state in the closure, follow transitions on arr[0]
        Set<NFAState> nextStates = new HashSet<>();
        for (NFAState state: currClosure) {
            Set<NFAState> transitions = getToState(state, arr[0]);
            if (transitions != null) {
                nextStates.addAll(transitions);
            }
        }

        Set<NFAState> allNextStates = new HashSet<>();
        for (NFAState next : nextStates) {
            allNextStates.add(next);
            allNextStates.addAll(eClosure(next));
        }

        // Calculate max copies again after checking nextStates
        copies[0] = Math.max(copies[0], allNextStates.size());

        // Recursive call for acceptsRecursive to get every branch possible
        Set<NFAState> result = new HashSet<>();
        for (NFAState next: nextStates) {
            Set<NFAState> trial = acceptsRecursive(newArr, next, copies);
            if (trial != null) {
                result.addAll(trial);
            }
        }

        return result.isEmpty() ? null : result;
    }


    /**
     * Simulates an NFA on input s to determine
     * whether the NFA accepts s.
     * 
     * @param s - the input string
     * @return true if s in the language of the NFA and false otherwise
     */
    @Override
    public boolean accepts(String s) {
        char[] trans = s.toCharArray();

        // Edge case: s contains an invalid transition
        for (char t: trans){
            if (!sigma.contains(t))
                return false;
        }

        // Fetch the Set of states the string 's' reaches 
        int[] dud = {1};
        Set<NFAState> results = acceptsRecursive(trans, init, dud);
        if (results == null) 
            return false;
        Iterator<NFAState> checkIfFinal = results.iterator();
        String test = results.toString();
        System.out.println(test);
        while(checkIfFinal.hasNext()){
            // Check all possible states reached by the string. If one is valid the string is valid
            NFAState checker = checkIfFinal.next();
            if (isFinal(checker.getName())) 
                return true;
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
        // if (!sigma.contains('e')){
        //     sigma.add('e');
        // }
        if (symbol == 'e'){
            // do nothing, e is reserved for epsilon transitions
        } else{
            sigma.add(symbol);
        }
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
        //return new NFAState();
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
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        // Check if toStates is not nothing
        if (toStates.size() == 0)
            return false;
        
        // Check if fromState isn't registered as a valid state
        if (getState(fromState) == null) 
            return false;

        // Check if onSymb is registered as a valid transition
        if (!sigma.contains(onSymb))
            return false;

        Character charToAdd = onSymb;
        Set<NFAState> toAdd = new HashSet<>();

        // Add every state that currently exists inside of the transition state
        // This is an ugly solution but it works 
        // Otherwise all old states pointed at by a transition would get erased by the new state
        HashMap<Character, Set<NFAState>> innerMap = transitionState.get(getState(fromState));
        if (innerMap != null && innerMap.get(charToAdd) != null){
            for (NFAState iter: innerMap.get(charToAdd)) {
                String test = toAdd.toString();
                System.out.println(test);
                toAdd.add(iter);
                test = toAdd.toString();
                System.out.println(test);
            }
        }


        if (!transitionState.containsKey(getState(fromState))) {
            transitionState.put(getState(fromState), new HashMap<>());

        } else {
            Iterator<String> iter = toStates.iterator();
            while (iter.hasNext()){
                NFAState next = getState(iter.next());

                // If no set exists for this character yet, create one
                if (!innerMap.containsKey(charToAdd)) {
                    innerMap.put(charToAdd, new LinkedHashSet<>());
                }
                // Add to the set
                innerMap.get(charToAdd).add(next);
            }
        }

        // Add every state that exists in the set of toStates
        Iterator<String> iter = toStates.iterator();
        while (iter.hasNext()){
            NFAState next = getState(iter.next());
            if (!states.contains(next)) // Check if current toState exists as a valid state
                return false;
            toAdd.add(next);
        }


        String test = transitionState.toString();
        System.out.println(test);
        transitionState.get(getState(fromState)).put(charToAdd, toAdd);
        test = transitionState.toString();
        System.out.println(test);
        return true;
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

    /**
     * 
     * @return deltaString
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
        for (NFAState temp : states) {
            for (Map.Entry<NFAState, HashMap<Character, Set<NFAState>>> entry : transitionState.entrySet()) {
                if (entry.getKey() == temp) {
                    sl.append(" " + temp.toString());
                    HashMap<Character, Set<NFAState>> innerMap = entry.getValue();
                    for (Character bet : sigma) {
                        for (HashMap.Entry<Character, Set<NFAState>> iter : innerMap.entrySet()) {
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
     * Checks if a given state "from" contains a transition equal to "symbol" which leads to state "to"
     * @param from
     * @param to
     * @param trans
     * @return true if from contains transition, false if otherwise
     */
    public boolean containsTrans(NFAState from, NFAState to, char trans) {
        // loop outer keys
        for (Map.Entry<NFAState, HashMap<Character, Set<NFAState>>> entry: transitionState.entrySet()) {
            if (entry.getKey() == from) {
                HashMap<Character, Set<NFAState>> innerMap = entry.getValue();

                for (HashMap.Entry<Character, Set<NFAState>> iter : innerMap.entrySet()) {
                    if (iter.getKey() == trans && iter.getValue().contains(to)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Return delta entries of states that are reachable from the "from" state by using "onSymb" transition
     * 
     * @param from   - the source state
     * @param onSymb - the label of the transition
     * @return a set of sink states
     */
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        Set<NFAState> retval = new LinkedHashSet<NFAState>();
        Iterator<NFAState> iter = states.iterator();

        // The symbol given is not a valid transition
        if (!sigma.contains(onSymb)) 
            return null;

        while (iter.hasNext()){
            NFAState next = iter.next();
            if (containsTrans(from, next, onSymb)){
                retval.add(next);
            }
        }

        return retval;
    }

    /**
     * Traverses all epsilon transitions and determine
     * what states can be reached from s through e
     * 
     * @param s
     * @return set of states that can be reached from s on epsilon trans.
     */

    public Set<NFAState> eClosure(NFAState s) {
        Set<NFAState> retval = new LinkedHashSet<NFAState>();
        retval.add(s);

        // run through state connected by s via 'e' and add each one to retval
        // once no states are reachable end function
        Set<NFAState> reachable = getToState(s, 'e');
        Iterator<NFAState> iter = reachable.iterator();
        for (int n = 0; n < reachable.size(); n++){
            NFAState next = iter.next();
            retval.add(next);
            retval.addAll(eClosure(next)); // addALL ignores duplicates (or at least it should as stated in its documentation)
        }

        return retval;
    }

    /**
     * Determines the maximum number of NFA copies
     * created when processing string s
     * Similar to how accept functions
     * 
     * @param s - the input string
     * @return - the maximum number of NFA copies created.
     */
    public int maxCopies(String s) {
        Set<NFAState> intialStates = eClosure(init);
        int[] copies = {intialStates.size()};

        if (s.equals("e")) 
            s = "";

        char[] arr = s.toCharArray();
        acceptsRecursive(arr, init, copies);
        int retval = copies[0];
        return retval;
    }

    /**
     * Determines if NFA is an instance of a NFA
     * 
     * @return - true if NFA's transition function has NFA's properties.
     */
    public boolean isDFA() {
        // keywords "returns true if NFA functions contain NFA's properties."
        // I do not believe here we have to convert NFA to NFA.
        // Proof by contradiction used, then

        // Does NFA have transitions leading to multiple states?
        Iterator<NFAState> iter = states.iterator();
        Iterator<Character> transition = sigma.iterator();
        while (iter.hasNext()){
            NFAState next = iter.next();
            // State contatins an epsilon transition and cannot be a NFA
            if (getToState(next, 'e').size() == 0) return false; 

            while (transition.hasNext()){
                char trans = transition.next();
                // State has multiple destinations on the same transition and cannot be a NFA
                if (getToState(next, trans).size() > 1) return false;
            }
        }

        return true;
    }
}
