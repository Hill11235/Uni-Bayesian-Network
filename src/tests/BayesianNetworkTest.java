package tests;

import org.junit.Test;
import support.Network;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void printBNB() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        networks.BNB.printNetwork();
        String expected = "Network: BNB\n" +
                "Node: J\n" +
                "J\t|\tp(J)\n" +
                "0\t|\t0.95\n" +
                "1\t|\t0.05\n" +
                "\n" +
                "Node: K\n" +
                "J\tK\t|\tp(K|J)\n" +
                "0\t0\t|\t0.3\n" +
                "0\t1\t|\t0.7\n" +
                "1\t0\t|\t0.1\n" +
                "1\t1\t|\t0.9\n" +
                "\n" +
                "Node: L\n" +
                "L\t|\tp(L)\n" +
                "0\t|\t0.3\n" +
                "1\t|\t0.7\n" +
                "\n" +
                "Node: M\n" +
                "K\tL\tM\t|\tp(M|K,L)\n" +
                "0\t0\t0\t|\t0.9\n" +
                "0\t0\t1\t|\t0.1\n" +
                "0\t1\t0\t|\t0.8\n" +
                "0\t1\t1\t|\t0.2\n" +
                "1\t0\t0\t|\t0.3\n" +
                "1\t0\t1\t|\t0.7\n" +
                "1\t1\t0\t|\t0.4\n" +
                "1\t1\t1\t|\t0.6\n" +
                "\n" +
                "Node: N\n" +
                "M\tN\t|\tp(N|M)\n" +
                "0\t0\t|\t0.8\n" +
                "0\t1\t|\t0.2\n" +
                "1\t0\t|\t0.4\n" +
                "1\t1\t|\t0.6\n" +
                "\n" +
                "Node: O\n" +
                "M\tO\t|\tp(O|M)\n" +
                "0\t0\t|\t0.2\n" +
                "0\t1\t|\t0.8\n" +
                "1\t0\t|\t0.95\n" +
                "1\t1\t|\t0.05\n" +
                "\n";
        assertEquals(outContent.toString(), expected);
        System.setOut(originalOut);
    }

}
