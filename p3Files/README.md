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
    make all (if makefile)
    javac tm/*.java (if no makefile)
Run the compiled class file with the command:
    java tm.TMSimulator paths/<filename>.txt
Console output will give the results after the program finishes.  

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

The TM class is split into various method calls that handle parsing, running, and extending. The parse mathod builds the three dimensional transition array from the file rules, and retrieves the amount of states and transition symbols required. The 3-dimensional array approach was due to it essentially being simple values that were being read or written, and was much quicker to run than a nested map, and all the associated overhead. The parse utilizes a nested for loop to accomplish the majority of the building from file. 

The run method works through the Turing Machine created and inserted into a 3d array by the parse method. Run works through that created Turing Machine using all the steps 
necessary to do so. Starting on state 0 with transition 0 the run method will then take the instructions given by Turing Machine to write into a tape and look at a new state
according to the value currently seen in the tape. Run will then move the head according to the L or R instruction. These steps repeat until the final state is reached.
A second tape is used for all negative values. This second tape, named negTape, is parsed and included in the resulting tape as the tape is printed.

We went for speed in the design of this project, and made the decision that the entire nested map structure in other iterations of the project would slow us down completely, and so we went with the 3 dimensional array. Once we had drawn out the tape and the rules from some of the example tests, we realized it would be necessary to have some sort of fall back for values on the negative side of the tape. We hoped to accomplish some sort of object oriented approach by make the TM seperate from the simulator, by utilizing it, as its own object. 

The final two methods in the TM class exist for the purpose of making the program run faster. getOutputLength and getOutputSum shave off barely any time noticeable by the human
eye. It was implemented because it could and it got rid of a for loop at the end of the runtime.


TESTING:
This program was tested to function with all given files and print the correct output. This program was not tested to run with bad input. If bad input is given, such as a 
Turing Machine which runs forever, the result will be an OutOfMemoryError.

DISCUSSION:
All errors encountered in the creation of this program were encountered mostly in the debugging process. There was no change to the logic.
The largest issue encountered was the OutOfMemoryError which throws whenever Java encounters a variable which contains a value too large for the process to handle.
This error popped up anytime the run method ran too long and extended the tape or negTape array one too many times. Fixing this issue required better understanding
of 3D arrays as a concept and the process a Turing Machine needs to work through before finding the final state. Changes from the tape and negTape originally being
char arrays before eventually making them integer arrays was one of these. Integer arrays are simply easier to work with as there is no fear or retrieving the wrong
value from them when looking for an integer.


EXTRA CREDIT:
We made a concerted attempt to make this program run quickly. Checking the time command on this program we can see the real time for this program to run is 1.717 
seconds on file 5, the largest and slowest out of all the provided files. There is a possibility this can run quicker, but that could be said about anything.

SOURCES:
ChatGPT was used is assistance with understanding how to properly cast a char variable into an int.
None of this AI generated code made it into the final version of the program.
