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

    /**
     * Tests that Nodes and be added and are included in the network.
     */
    @Test
    public void testAddAndGetNode() {
        BayesianNetwork bn = new BayesianNetwork("TEST");
        Node nd = new Node("testNode", null);
        bn.addNode(nd);
        ArrayList<Node> nodes = bn.getNodes();
        assertEquals(nodes.size(), 1);
        assertTrue(nodes.contains(nd));
    }

    /**
     * Tests that Nodes can be fetched with a label.
     */
    @Test
    public void testGetNodeWithLabel() {
        BayesianNetwork bn = networks.BNA;
        Node nodeD = bn.getNode("D");
        assertEquals("D", nodeD.getLabel());
    }

    /**
     * Tests that given a list of Nodes we get back the correct list of labels in the correct order.
     */
    @Test
    public void testGetLabels() {
        BayesianNetwork bn = networks.BNA;
        Node nodeD = bn.getNode("D");
        ArrayList<Node> ancestors = nodeD.getAllAncestors();
        ArrayList<String> labels = bn.getLabelList(ancestors);
        assertTrue(labels.size() == 3);
        assertTrue(labels.contains("A"));
        assertTrue(labels.contains("B"));
        assertTrue(labels.contains("C"));
    }

    /**
     * Tests that the CNX network is printed correctly.
     */
    @Test
    public void printCNX() {
        //TODO implement network and test printed correctly.
    }

    /**
     * Tests that the BNA network is printed correctly.
     */
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

    /**
     * Tests that the BNB network is printed correctly.
     */
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

    /**
     * Tests that the BNC network is printed correctly.
     */
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

    /**
     * Tests that the BND network is printed correctly.
     */
    @Test
    public void printBND() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        networks.BND.printNetwork();
        String expected = "Network: BND\n" +
                "Node: W\n" +
                "W\t|\tp(W)\n" +
                "0\t|\t0.75\n" +
                "1\t|\t0.25\n" +
                "\n" +
                "Node: S\n" +
                "W\tS\t|\tp(S|W)\n" +
                "0\t0\t|\t0.98\n" +
                "0\t1\t|\t0.02\n" +
                "1\t0\t|\t0.8\n" +
                "1\t1\t|\t0.2\n" +
                "\n" +
                "Node: E\n" +
                "S\tE\t|\tp(E|S)\n" +
                "0\t0\t|\t0.99\n" +
                "0\t1\t|\t0.01\n" +
                "1\t0\t|\t0.9\n" +
                "1\t1\t|\t0.1\n" +
                "\n" +
                "Node: D\n" +
                "S\tE\tD\t|\tp(D|S,E)\n" +
                "0\t0\t0\t|\t0.95\n" +
                "0\t0\t1\t|\t0.05\n" +
                "0\t1\t0\t|\t0.2\n" +
                "0\t1\t1\t|\t0.8\n" +
                "1\t0\t0\t|\t0.3\n" +
                "1\t0\t1\t|\t0.7\n" +
                "1\t1\t0\t|\t0.05\n" +
                "1\t1\t1\t|\t0.95\n" +
                "\n" +
                "Node: R\n" +
                "R\t|\tp(R)\n" +
                "0\t|\t0.99\n" +
                "1\t|\t0.01\n" +
                "\n" +
                "Node: B\n" +
                "R\tB\t|\tp(B|R)\n" +
                "0\t0\t|\t0.9\n" +
                "0\t1\t|\t0.1\n" +
                "1\t0\t|\t0.2\n" +
                "1\t1\t|\t0.8\n" +
                "\n" +
                "Node: L\n" +
                "D\tB\tL\t|\tp(L|D,B)\n" +
                "0\t0\t0\t|\t0.96\n" +
                "0\t0\t1\t|\t0.04\n" +
                "0\t1\t0\t|\t0.3\n" +
                "0\t1\t1\t|\t0.7\n" +
                "1\t0\t0\t|\t0.1\n" +
                "1\t0\t1\t|\t0.9\n" +
                "1\t1\t0\t|\t0.05\n" +
                "1\t1\t1\t|\t0.95\n" +
                "\n";
        assertEquals(outContent.toString(), expected);
        System.setOut(originalOut);
    }
}
