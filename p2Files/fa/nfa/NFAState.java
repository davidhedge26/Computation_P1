package fa.nfa;
import fa.State;

public class NFAState extends State {
    public NFAState() {

    }

    public NFAState(String name) {
        super(name);
    }

    /**
     * When given a certain transition return the state that transition leads to
     */
    public NFAState toStates(char given) {
        return null;
    }
}
