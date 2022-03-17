package tests;

import org.junit.Before;
import org.junit.Test;
import support.Node;

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

    @Before
    public void setUp() {
        initialNode = new Node("A", null);
        secondaryNode = new Node("B", null);
        childNode = new Node("C", new ArrayList<>(Arrays.asList(initialNode, secondaryNode)));
        printTestNode = new Node("D", new ArrayList<>(Arrays.asList(initialNode, secondaryNode, childNode)));
    }

    //TODO set the CPT and check the correct information is returned
    //TODO needs to print for Nodes without a parent node as well
    @Test
    public void testCPT() {

    }

    //TODO test that the variables and probabilities are printed as expected
    @Test
    public void testPrint() {
        printTestNode.printNode();
    }

    @Test
    public void testGetParentNodes() {
        ArrayList<Node> parents = childNode.getParents();
        ArrayList<Node> expected = new ArrayList<>(Arrays.asList(initialNode, secondaryNode));
        assertEquals(parents, expected);
    }

    //TODO check that children are being added via Node constructor
    @Test
    public void testChildren() {
        initialNode.addChildren(childNode);
        assertTrue(initialNode.getChildren().contains(childNode));
    }

    @Test
    public void testGetLabel() {
        assertEquals(initialNode.getLabel(), "A");
        assertEquals(secondaryNode.getLabel(), "B");
        assertEquals(childNode.getLabel(), "C");
    }
}
