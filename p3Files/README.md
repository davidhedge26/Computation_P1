****************
* Project - 3
* Class CS361 Sect 002
* Date 4/24/2026
* David Hedge, Jared Guidry
****************
OVERVIEW:
TMSimulator - simulates a turing machine, by intaking a set of rules, that act as the machine instantiation,
and accepts or rejects a string, by utilizing a tape with a moveable read/write head.  

INCLUDED FILES:
List the files required for the project with a brief
explanation of why each is included.
e.g.
* TM.java - Turing Machine Itself, that holds all the logic
* TMSimulator.java - driver file, that has the main thread
* README - this file
* Makefile - compiles the program

COMPILING AND RUNNING:
From the directory containing all source files, compile the
driver class (and all dependencies) with the command:
make all(if makefile)
javac tm/*.java
Run the compiled class file with the command:
java tm.TMSimulator paths/file5.txt
Console output will give the results after the program finishes.  

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

The TM class is split into various method calls that handle parsing, running, and extending. The parse mathod builds the three dimensional transition array from the file rules, and retrieves the amount of states and transition symbols required. The 3-dimensional array approach, was due to it essentially being simple values that were being read or written, and was much quicker to run than a nested map, and all the associated overhead. The parse utilizes a nested for loop to accomplish the majority of the building from file. The run method relies on a while loop to move through the necessary transitions and the multi-tape setup. The multi-tape approach is so that negative indices can be translated to an additional tape.

We went for speed in the design of this project, and made the decision that the entire nested map structure in other iterations of the project would slow us down completely, and so we went with the 3 dimensional array. Once we had drawn out the tape and the rules from some of the example tests, we realized it would be necessary to have some sort of fall back for values on the negative side of the tape. We hoped to accomplish some sort of object oriented approach by make the TM seperate from the simulator, by utilizing it, as its own object.


This is the sort of information someone who really wants to
understand your program - possibly to make future enhancements -
would want to know.
Explain the main concepts and organization of your program so that
the reader can understand how your program works. This is not a repeat
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