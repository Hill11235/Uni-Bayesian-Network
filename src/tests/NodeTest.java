package tests;

import org.junit.Before;
import org.junit.Test;
import support.BayesianNetwork;
import support.Factor;
import support.Network;
import support.Node;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests Node class and its methods.
 */
public class NodeTest {

    private Node initialNode;
    private Node secondaryNode;
    private Node childNode;
    private Node printTestNode;
    private HashMap<String, Double> probs;

    /**
     * Set up Nodes and some probabilities before each test.
     */
    @Before
    public void setUp() {
        initialNode = new Node("A", null);
        secondaryNode = new Node("B", null);
        childNode = new Node("C", new ArrayList<>(Arrays.asList(initialNode, secondaryNode)));
        printTestNode = new Node("D", new ArrayList<>(Arrays.asList(initialNode, secondaryNode, childNode)));
        probs = addProb();

        Double d1 = 0.4;
        Double d2 = 0.6;
        initialNode.addCPTvalues(d1, d2);
    }

    /**
     * Test that the correct CPT is set and returned.
     */
    @Test
    public void testCPT() {
        Factor cpt = printTestNode.getCpt();
        assertEquals(cpt.getProbabilities(), probs);
    }

    /**
     * Test that the output is printed for a Node with three parents.
     */
    @Test
    public void testPrintMulti() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        printTestNode.printNode();
        String expected = "A\tB\tC\tD\t|\tp(D|A,B,C)\n" +
                "0\t0\t0\t0\t|\t0.1\n" +
                "0\t0\t0\t1\t|\t0.9\n" +
                "0\t0\t1\t0\t|\t0.2\n" +
                "0\t0\t1\t1\t|\t0.8\n" +
                "0\t1\t0\t0\t|\t0.3\n" +
                "0\t1\t0\t1\t|\t0.7\n" +
                "0\t1\t1\t0\t|\t0.4\n" +
                "0\t1\t1\t1\t|\t0.6\n" +
                "1\t0\t0\t0\t|\t0.5\n" +
                "1\t0\t0\t1\t|\t0.5\n" +
                "1\t0\t1\t0\t|\t0.15\n" +
                "1\t0\t1\t1\t|\t0.85\n" +
                "1\t1\t0\t0\t|\t0.25\n" +
                "1\t1\t0\t1\t|\t0.75\n" +
                "1\t1\t1\t0\t|\t0.35\n" +
                "1\t1\t1\t1\t|\t0.65\n";
        assertEquals(outContent.toString(), expected);
        System.setOut(originalOut);
    }

    /**
     * Test that the output is printed for a Node with no parents.
     */
    @Test
    public void testPrintSingle() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        initialNode.printNode();
        String expected = "A\t|\tp(A)\n" +
                "0\t|\t0.4\n" +
                "1\t|\t0.6\n";
        assertEquals(outContent.toString(), expected);
        System.setOut(originalOut);
    }

    /**
     * Test that the correct parent Nodes are returned.
     */
    @Test
    public void testGetParentNodes() {
        ArrayList<Node> parents = childNode.getParents();
        ArrayList<Node> expected = new ArrayList<>(Arrays.asList(initialNode, secondaryNode));
        assertEquals(parents, expected);
    }

    /**
     * Test that the addChildren method works and that children are added via the Node constructor as well.
     */
    @Test
    public void testChildren() {
        //Test that add method works
        initialNode.addChildren(childNode);
        assertTrue(initialNode.getChildren().contains(childNode));

        //Test that all Nodes are being added through the Node constructor
        assertTrue(initialNode.getChildren().contains(printTestNode));
    }

    /**
     * Test that the correct label is returned.
     */
    @Test
    public void testGetLabel() {
        assertEquals(initialNode.getLabel(), "A");
        assertEquals(secondaryNode.getLabel(), "B");
        assertEquals(childNode.getLabel(), "C");
    }

    /**
     * Check that the list of ancestor Nodes is correctly calculated and returned.
     */
    @Test
    public void testGetAncestors1() {
        Network network = new Network();
        BayesianNetwork bn = network.BNC;
        Node nd = bn.getNode("U");
        ArrayList<Node> ancestors = nd.getAllAncestors();
        assertEquals(ancestors.size(), 4);
    }

    /**
     * Check that the list of ancestor Nodes is correctly calculated and returned for a Node with no parents.
     */
    @Test
    public void testGetAncestors2() {
        Network network = new Network();
        BayesianNetwork bn = network.BNC;
        Node nd = bn.getNode("R");
        ArrayList<Node> ancestors = nd.getAllAncestors();
        assertEquals(ancestors.size(), 0);
    }

    /**
     * Tests that adding a single parent works as intended.
     */
    @Test
    public void testAddParent() {
        Node first = new Node("A", null);
        Node second = new Node("B", new ArrayList<>(List.of(first)));

        assertEquals(first.getParents().size(), 0);
        first.addParent(second);
        assertEquals(first.getParents().size(), 1);
        assertTrue(first.getParents().contains(second));
    }

    /**
     * Helper method used to add 16 probabilities to Node.
     * @return a list of the probabilities for comparison.
     */
    private HashMap<String, Double> addProb() {
        Double d1 = 0.1;
        Double d2 = 0.9;
        Double d3 = 0.2;
        Double d4 = 0.8;
        Double d5 = 0.3;
        Double d6 = 0.7;
        Double d7 = 0.4;
        Double d8 = 0.6;
        Double d9 = 0.5;
        Double d10 = 0.5;
        Double d11 = 0.15;
        Double d12 = 0.85;
        Double d13 = 0.25;
        Double d14 = 0.75;
        Double d15 = 0.35;
        Double d16 = 0.65;
        ArrayList<Double> probList = new ArrayList<>(Arrays.asList(d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16));

        printTestNode.addCPTvalues(d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16);
        HashMap<String, Double> output = new HashMap<>();

        int i = 0;
        for (Double prob : probList) {
            int repeat = 4 - Integer.toBinaryString(i).length();
            String truths = "0".repeat(repeat) + Integer.toBinaryString(i);
            output.put(truths, prob);
            i++;
        }
        return output;
    }
}
