****************
* P1 DFA 
* Class - CS 361 Theory of Computation
* Date - 02-20-26
* Your name - David Hedge and Jared Guidry
****************
OVERVIEW:
Java program, builds, modifies, and runs Definite finite automata. 
INCLUDED FILES:
List the files required for the project with a brief
explanation of why each is included.

e.g.
* DFAinterface.java - source file
* FAInterface.java - source file
* DFAState.java - node core
* DFA.java - State machine core
* README - this file

COMPILING AND RUNNING:
Give the command for compiling the program, the command
for running the program, and any usage instructions the
user needs.
These are command-line instructions for a system like onyx.
They have nothing to do with Eclipse or any other IDE. They
must be specific - assume the user has Java installed, but
has no idea how to compile or run a Java program from the
command-line.
e.g.
From the directory containing all source files, compile the
driver class (and all dependencies) with the command:
$ javac Class1.java
Run the compiled class file with the command:
$ java Class1

The j-unit test cases will output test results of various DFA objects.
PROGRAM DESIGN AND IMPORTANT CONCEPTS:

Program Is a definitive finite automata. The core strucutre of a DFA relies on the five tuple.

The program is for the class named DFA, this consists of 5 object structures or tuples. States,alphabet,start state, final state, transition table. It was important in the instructions that the structures maintained ordering. In the 5 tuples, you will see a lot of linked hash sets, and linked hash maps to ensure, order is maintained.

The transitionState nested map structure was used to represent the delta structure of the transition table. It was important to be cautious about structure selection inorder to achieve long term, modularity between the State and the DFAState object, and all the interface objects. Object orientation and careful data structure selection, along with an object oriented approach in the code logic, ensured the early test cases were relatively simple to troubleshoot.

The class object DFA is a concrete implementation for DFAinterface. Key methods were implemented: toString, addTransition, and swap from the interface, in addition to helper methods needed. 

Most of the methods consist of interating through one of the 5-tuple data structures, but then when it comes to some print statements, careful checking on how the ordering in the states set is important in the printout of the delta data structure object. Many methods work similarly where a condition is checked whether some object structure has a certain item, and something is added, or something is returned.

It was important to expand on the junit test cases and see if our DFA implementation could handle more transition states, or more swaps.











of javadoc comments or an exhaustive listing of all methods, but an
explanation of the critical algorithms and object interactions that make
up the program.
Explain the main responsibilities of the classes and interfaces that make
up the program. Explain how the classes work together to achieve the
program
goals. If there are critical algorithms that a user should understand,
explain them as well.
If you were responsible for designing the program's classes and choosing
how they work together, why did you design the program this way? What, if
anything, could be improved?
TESTING:
How did you test your program to be sure it works and meets all of the
requirements? What was the testing strategy? What kinds of tests were
run?
Can your program handle bad input? Is your program idiot-proof? How do
you
know? What are the known issues / bugs remaining in your program?
DISCUSSION:
Discuss the issues you encountered during programming (development)
and testing. What problems did you have? What did you have to research
and learn on your own? What kinds of errors did you get? How did you
fix them?
What parts of the project did you find challenging? Is there anything
that finally "clicked" for you in the process of working on this project?
EXTRA CREDIT:
If the project had opportunities for extra credit that you attempted,
be sure to call it out so the grader does not overlook it.
SOURCES:
All sources used outside of lecture notes, slides, and the textbook need
to
be cited here. If you used websites, used GenAI, asked your dad or your
boss
or your roommate for help then you must cite those resources. I am not
concerned if you use proper APA or MLA or another format as long as you
include
all relevant information. If it is a person or GenAI that you referenced,
be
sure to include who you talked to (or which AI you accessed), when you
talked
to them, and what help they provided (e.g. Student, Awesome. Private
communication, 21 January 2026. Discussed how polymorphism allows the
return
types of methods implemented in a class to be different from the class
specified
in the interface as long as the <type in implementation> “is-a” <type in
interface>.)
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