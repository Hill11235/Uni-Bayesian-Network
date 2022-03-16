package tests;

import org.junit.Before;
import org.junit.Test;
import support.Node;

import static org.junit.Assert.assertEquals;

public class NodeTest {

    private Node initialNode;

    @Before
    public void setUp() {
        initialNode = new Node("A", null);
    }

    @Test
    public void testPrint() {

    }

    @Test
    public void testGetParentNodes() {

    }

    @Test
    public void testSetChildren() {

    }

    @Test
    public void testGetChildren() {

    }

    @Test
    public void testGetLabel() {
        assertEquals(initialNode.getLabel(), "A");
    }
}
