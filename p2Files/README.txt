****************
* P2 NFA
* Class - CS 361 Theory of Computation
* Date - 03-23-26
* Name - David Hedge and Jared Guidry
****************
## OVERVIEW:
Java program, builds, modifies, and runs nodeterministic finite automata. 
INCLUDED FILES:
List the files required for the project with a brief
explanation of why each is included.

e.g.
* NFAinterface.java - source file
* FAInterface.java - source file
* State.java - source node file
* NFAState.java - node file
* NFA.java - State machine, heart of the program
* README - this file

## COMPILING AND RUNNING:
Compile by running the below command in directory "p2files"
 - javac fa/nfa/*.Java
 
Then nfa can be run through NFATest.java directly.
Otherwise NFA is a support program run in conjunction with another program.

## PROGRAM DESIGN AND IMPORTANT CONCEPTS:
Program makes and models a nodeterministic finite automata.

The program is for the class named NFA, this consists of 5 object structures or tuples. Unlike a DFA, however, 
the NFA can have multiple of the same transition leadto different states. An NFA also has access to the epsilon transition 
which is simply an empty string that can still connect two states together.
States, alphabet, start state, final state, transition table are the elements of the 5 tuple. It was important in the 
instructions that the structures maintained ordering. In the 5 tuples, you will see a lot of linked hash sets, and 
linked hash maps to ensure, order is maintained.

The transitionState nested map structure was used to represent the delta structure of the transition table. It was 
important to be cautious about structure selection in order to achieve long term, modularity between the State and 
the NFAState object, and all the interface objects. Object orientation and careful data structure selection, along 
with an object oriented approach in the code logic, ensured the early test cases were relatively simple to troubleshoot.

The class object NFA is a concrete implementation for NFAinterface. Other than the states provided by the interface
functions from DFA were borrowed. These functions include addState, getters and setters for initial and final,
accept, addTransition, toString, containsTrans, etc. Each were edited to work with the NFA structure. Other than the 
functions borrowed there were functions added unique to the NFA. These were eClosure, acceptsRecursive (helper function
made for the accepts function to work with an NFA's branching nature), getToState, maxCopies, and isDFA.

Most of the methods consist of interating through one of the 5-tuple data structures, but then when it comes to some 
print statements, careful checking on how the ordering in the states set is important in the printout of the delta data 
structure object. Many methods work similarly where a condition is checked whether some object structure has a certain 
item, and something is added, or something is returned.


## TESTING:
It was important to expand on the junit test cases and see if our NFA implementation because of how different an NFA is from
a DFA. Contains Transition had to be tested extensively due to multiple issues in the act of translating from a DFA to an NFA.
Junit was used to test this program by ensuring functionality of each
broad function in NFA.java. Then Junit was used to ensure that function 
worked on NFA of any size.

## DISCUSSION:
Some problems noted. "nfa.getState("I").toStates('1'), Set.of(nfa.getState("I"))" always returns null
"assertEquals(nfa.maxCopies("1111"), 4);" will return 3 and not 4. This is the only test case I can seem to make this occur for.
All other test cases, created or provided, pass.

David Hedge -> I had a lot of trouble transitioning this project from a DFA into an NFA. Artifacts from the DFA haunted me the 
entire time. This was especially prevalent in debugging the containsTrans() function which had an issue where it would remove 
old states, then it had an issue where it would remove old transitions to those states not allowing duplicates. My solution was 
ugly but it works. It was all I could manage while making the project mostly on my own.
  
Jared Guidry -> 

## EXTRA CREDIT:
If the project had opportunities for extra credit that you attempted,
be sure to call it out so the grader does not overlook it.

## SOURCES:
David Hedge -> Claude used to provide a second set of eyes needed in the debugging process on the maxCopies function.
                All that was provided to Claude was the function itself, picking and choosing what Claude made was 
                necessary to ensure generated code worked properly with the rest of the program.

Jared Guidry -> 
----------------------------------------------------------------------------