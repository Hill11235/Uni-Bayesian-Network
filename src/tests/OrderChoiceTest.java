package tests;

import org.junit.Before;
import org.junit.Test;
import support.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderChoiceTest {

    private OrderChoice oc;
    private Network network = new Network();

    @Before
    public void setUp() {
    }

    @Test
    public void testCreateUndirectedGraph() {

    }

    @Test
    public void addLinksBetweenParents() {

    }

    @Test
    public void testAreParentsConnected(){

    }

    @Test
    public void testConnectParents() {

    }

    @Test
    public void getParentComboFromSet() {

    }

    /**
     * Tests that the parents for each Node are correct after adding further parents.
     */
    @Test
    public void testAddParentForEachChild() {
        BayesianNetwork bn = network.BNA;
        oc = new OrderChoice(bn, "A");

        Node first = new Node("A", null);
        Node second = new Node("B", new ArrayList<>(List.of(first)));
        Node third = new Node("C",new ArrayList<>(List.of(first, second)));

        assertEquals(first.getParents().size(), 0);
        oc.addParentForEachChild(new ArrayList<>(List.of(first, second, third)));
        assertEquals(first.getParents().size(), 2);
        assertTrue(first.getParents().contains(second));
        assertTrue(first.getParents().contains(third));
    }

    /**
     * Simple test to check that a list of the first n integers is generated.
     */
    @Test
    public void testGetIntegerList() {
        BayesianNetwork bn = network.BNA;
        oc = new OrderChoice(bn, "A");

        ArrayList<Integer> masterSet = oc.getIntegerList(5);

        ArrayList<Integer> comparison = new ArrayList<>(Arrays.asList(0,1,2,3,4));
        assertEquals(masterSet, comparison);
    }

    /**
     * TAKEN FROM MY PREVIOUS CS5011 ASSIGNMENT WHERE I WROTE THIS K-COMBINATIONS METHOD.
     * Tests that the correct combinations are returned.
     */
    @Test
    public void testGetKCombinations() {
        BayesianNetwork bn = network.BNA;
        oc = new OrderChoice(bn, "A");

        ArrayList<Integer> masterSet = oc.getIntegerList(4);

        //test size correct
        assertEquals(6, oc.getKCombinations(masterSet, 2).size());
        assertEquals(4, oc.getKCombinations(masterSet, 1).size());

        //test contents correct
        String output4Choose2 = "[[0, 1], [0, 2], [0, 3], [1, 2], [1, 3], [2, 3]]";
        List<Set<Integer>> listCombo = oc.getKCombinations(masterSet, 2);
        assertEquals(output4Choose2, listCombo.toString());
    }
}
