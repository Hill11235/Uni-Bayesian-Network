package tests;

import org.junit.Test;
import support.BayesianNetwork;
import support.Network;
import support.Node;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Testing BayesianNetwork class.
 */
public class BayesianNetworkTest {

    Network networks = new Network();
    //TODO print test for the CNX network to be finalised

    @Test
    public void testAddAndGetNode() {
        BayesianNetwork bn = new BayesianNetwork("TEST");
        Node nd = new Node("testNode", null);
        bn.addNode(nd);
        ArrayList<Node> nodes = bn.getNodes();
        assertEquals(nodes.size(), 1);
        assertTrue(nodes.contains(nd));
    }

    @Test
    public void testGetNodeWithLabel() {
        BayesianNetwork bn = networks.BNA;
        Node nodeD = bn.getNode("D");
        assertTrue(nodeD.getLabel().equals("D"));
    }

    @Test
    public void printCNX() {
        //TODO implement network and test printed correctly.
    }

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

    @Test
    public void printBNC() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        networks.BNC.printNetwork();
        String expected = "Network: BNC\n" +
                "Node: P\n" +
                "P\t|\tp(P)\n" +
                "0\t|\t0.95\n" +
                "1\t|\t0.05\n" +
                "\n" +
                "Node: Q\n" +
                "P\tQ\t|\tp(Q|P)\n" +
                "0\t0\t|\t0.3\n" +
                "0\t1\t|\t0.7\n" +
                "1\t0\t|\t0.1\n" +
                "1\t1\t|\t0.9\n" +
                "\n" +
                "Node: R\n" +
                "R\t|\tp(R)\n" +
                "0\t|\t0.3\n" +
                "1\t|\t0.7\n" +
                "\n" +
                "Node: S\n" +
                "Q\tR\tS\t|\tp(S|Q,R)\n" +
                "0\t0\t0\t|\t0.9\n" +
                "0\t0\t1\t|\t0.1\n" +
                "0\t1\t0\t|\t0.8\n" +
                "0\t1\t1\t|\t0.2\n" +
                "1\t0\t0\t|\t0.3\n" +
                "1\t0\t1\t|\t0.7\n" +
                "1\t1\t0\t|\t0.4\n" +
                "1\t1\t1\t|\t0.6\n" +
                "\n" +
                "Node: U\n" +
                "S\tU\t|\tp(U|S)\n" +
                "0\t0\t|\t0.2\n" +
                "0\t1\t|\t0.8\n" +
                "1\t0\t|\t0.95\n" +
                "1\t1\t|\t0.05\n" +
                "\n" +
                "Node: V\n" +
                "Q\tR\tV\t|\tp(V|Q,R)\n" +
                "0\t0\t0\t|\t0.9\n" +
                "0\t0\t1\t|\t0.1\n" +
                "0\t1\t0\t|\t0.85\n" +
                "0\t1\t1\t|\t0.15\n" +
                "1\t0\t0\t|\t0.45\n" +
                "1\t0\t1\t|\t0.55\n" +
                "1\t1\t0\t|\t0.3\n" +
                "1\t1\t1\t|\t0.7\n" +
                "\n" +
                "Node: Z\n" +
                "V\tS\tZ\t|\tp(Z|V,S)\n" +
                "0\t0\t0\t|\t0.8\n" +
                "0\t0\t1\t|\t0.2\n" +
                "0\t1\t0\t|\t0.6\n" +
                "0\t1\t1\t|\t0.4\n" +
                "1\t0\t0\t|\t0.3\n" +
                "1\t0\t1\t|\t0.7\n" +
                "1\t1\t0\t|\t0.35\n" +
                "1\t1\t1\t|\t0.65\n" +
                "\n";
        assertEquals(outContent.toString(), expected);
        System.setOut(originalOut);
    }
}
