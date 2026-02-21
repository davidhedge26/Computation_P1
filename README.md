****************
* P1 DFA 
* Class - CS 361 Theory of Computation
* Date - 02-20-26
* Name - David Hedge and Jared Guidry
****************
## OVERVIEW:
Java program, builds, modifies, and runs Definite finite automata. 
INCLUDED FILES:
List the files required for the project with a brief
explanation of why each is included.

e.g.
* DFAinterface.java - source file
* FAInterface.java - source file
* State.java - source node file
* DFAState.java - node core
* DFA.java - State machine core
* README - this file

## COMPILING AND RUNNING:
Compile by running the below command in directories "fa" and "fa/dfa"
* javac *.java
Then dfa can be run through DFATest.java directly.
Otherwise DFA is a support program run in conjunction with another program.

## PROGRAM DESIGN AND IMPORTANT CONCEPTS:
Program Is a definitive finite automata. The core strucutre of a DFA relies on the five tuple.

The program is for the class named DFA, this consists of 5 object structures or tuples. States,alphabet,start state, final state, transition table. It was important in the instructions that the structures maintained ordering. In the 5 tuples, you will see a lot of linked hash sets, and linked hash maps to ensure, order is maintained.

The transitionState nested map structure was used to represent the delta structure of the transition table. It was important to be cautious about structure selection inorder to achieve long term, modularity between the State and the DFAState object, and all the interface objects. Object orientation and careful data structure selection, along with an object oriented approach in the code logic, ensured the early test cases were relatively simple to troubleshoot.

The class object DFA is a concrete implementation for DFAinterface. Key methods were implemented: toString, addTransition, and swap from the interface, in addition to helper methods needed. 

Most of the methods consist of interating through one of the 5-tuple data structures, but then when it comes to some print statements, careful checking on how the ordering in the states set is important in the printout of the delta data structure object. Many methods work similarly where a condition is checked whether some object structure has a certain item, and something is added, or something is returned.

It was important to expand on the junit test cases and see if our DFA implementation could handle more transition states, or more swaps.

## TESTING:
Junit was used to test this program by ensuring functionality of each
broad function in DFA.java. Then Junit was used to ensure that function 
worked on DFA of any size.

## DISCUSSION:
No major problems noted. Swap only works on machines with 2 accepted transitions.

David Hedge -> I had trouble at first learning how everything was supposed to click together
in this project but after I figured out the broader picture the project gained a flow.

## EXTRA CREDIT:
If the project had opportunities for extra credit that you attempted,
be sure to call it out so the grader does not overlook it.

## SOURCES:
David Hedge -> no sources used.
--------------------------------------------------------------------------
--
All content in a README file is expected to be written in clear English
with
proper grammar, spelling, and punctuation. If you are not a strong writer,
be sure to get someone else to help you with proofreading. Consider all
project
documentation to be professional writing for your boss and/or potential
customers.
Annotations