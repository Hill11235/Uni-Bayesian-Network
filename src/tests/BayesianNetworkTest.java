package tests;

import org.junit.Before;
import org.junit.Test;
import support.Factor;
import support.Network;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Testing BayesianNetwork class.
 */
public class BayesianNetworkTest {

    Network networks = new Network();
    //TODO addNode test
    //TODO print test for each of the implemented networks
    //TODO getNodes test

    @Test
    public void printBNA() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        networks.BNA.printNetwork();
        String expected = "Network: BNA\n" +
                "Node: A\n" +
                "A\t|\tp(A)\n" +
                "0\t|\t0.95\n" +
                "1\t|\t0.05\n" +
                "\n" +
                "Node: B\n" +
                "A\tB\t|\tp(B|A)\n" +
                "0\t0\t|\t0.2\n" +
                "0\t1\t|\t0.8\n" +
                "1\t0\t|\t0.95\n" +
                "1\t1\t|\t0.05\n" +
                "\n" +
                "Node: C\n" +
                "B\tC\t|\tp(C|B)\n" +
                "0\t0\t|\t0.7\n" +
                "0\t1\t|\t0.3\n" +
                "1\t0\t|\t0.9\n" +
                "1\t1\t|\t0.1\n" +
                "\n" +
                "Node: D\n" +
                "C\tD\t|\tp(D|C)\n" +
                "0\t0\t|\t0.4\n" +
                "0\t1\t|\t0.6\n" +
                "1\t0\t|\t0.6\n" +
                "1\t1\t|\t0.4\n" +
                "\n";
        assertEquals(outContent.toString(), expected);
        System.setOut(originalOut);
    }

}
