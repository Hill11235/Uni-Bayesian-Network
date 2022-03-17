package tests;

import org.junit.Before;
import org.junit.Test;
import support.Factor;
import support.Node;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

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
    private ArrayList<Double> probs;

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

    @Test
    public void testCPT() {
        Factor cpt = printTestNode.getCpt();
        assertEquals(cpt.getProbabilities(), probs);
    }

    //TODO needs to print for Nodes without a parent node as well
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

    @Test
    public void testPrintSingle() {
        initialNode.printNode();
    }

    @Test
    public void testGetParentNodes() {
        ArrayList<Node> parents = childNode.getParents();
        ArrayList<Node> expected = new ArrayList<>(Arrays.asList(initialNode, secondaryNode));
        assertEquals(parents, expected);
    }

    @Test
    public void testChildren() {
        //Test that add method works
        initialNode.addChildren(childNode);
        assertTrue(initialNode.getChildren().contains(childNode));

        //Test that all Nodes are being added through the Node constructor
        assertTrue(initialNode.getChildren().contains(printTestNode));
    }

    @Test
    public void testGetLabel() {
        assertEquals(initialNode.getLabel(), "A");
        assertEquals(secondaryNode.getLabel(), "B");
        assertEquals(childNode.getLabel(), "C");
    }

    private ArrayList<Double> addProb() {
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

        printTestNode.addCPTvalues(d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16);
        return new ArrayList<>(Arrays.asList(d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16));
    }
}
