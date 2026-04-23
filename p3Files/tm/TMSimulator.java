package tm;

/**
 * TMSimulator class is for running TM.java
 * compile through Makefile command "make all"
 * run in terminal through below command
 *      java tm/TMSimultor.java paths/<name-of-file>.txt
 */
public class TMSimulator {
    /**
     * Main function for the TM class
     * Given the file path of a turing machine .txt file the main function will make and manage a turing machine
     * Main will then output the resulting tape and the necessary information for that resulting tape
     * @param args
     */
    public static void main(String[] args) {
        TM machine = new TM(args[0]);
        String result = machine.run();


        // Create and build the final output to be sent to stdout
        int sumOfSymbols = 0;
        int outputLength = 0;
        while (outputLength < result.length()){
            sumOfSymbols += result.charAt(outputLength) - '0';
            outputLength++;
        }
        System.out.println("Resulting tape = " + result);
        System.out.println("Output Length = " + outputLength);
        System.out.println("Sum of Symbols = " + sumOfSymbols);
    }
}
