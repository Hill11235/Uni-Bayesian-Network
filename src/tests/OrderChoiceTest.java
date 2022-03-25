package tests;

import org.junit.Test;
import support.BayesianNetwork;
import support.Network;
import support.Node;
import support.OrderChoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests search algorithms and underlying helper methods in OrderChoice class.
 */
public class OrderChoiceTest {

    private OrderChoice oc;
    private final Network network = new Network();

    /**
     * Tests correct path is found by max cardinality search for CNX.
     */
    @Test
    public void searchMaxCNX() {
        //TODO implement
    }

    /**
     * Tests correct path is found by greedy search for CNX.
     */
    @Test
    public void searchGreedyCNX() {
        //TODO implement
    }

    /**
     * Tests correct path is found by max cardinality search for BNA.
     */
    @Test
    public void searchMaxBNA() {
        BayesianNetwork bn = network.BNA;
        oc = new OrderChoice(bn, "A");
        String[] order = oc.search("MAX");
        String[] expectedOrder = {"D", "C", "B"};

        assertEquals(order, expectedOrder);
    }

    /**
     * Tests correct path is found by greedy search for BNA.
     */
    @Test
    public void searchGreedyBNA() {
        BayesianNetwork bn = network.BNA;
        oc = new OrderChoice(bn, "A");
        String[] order = oc.search("GREEDY");
        String[] expectedOrder = {"D", "B", "C"};

        assertEquals(order, expectedOrder);
    }

    /**
     * Tests correct path is found by max cardinality search for BNB.
     */
    @Test
    public void searchMaxBNB() {
        BayesianNetwork bn = network.BNB;
        oc = new OrderChoice(bn, "J");
        String[] order = oc.search("MAX");
        String[] expectedOrder = {"O", "N", "M", "L", "K"};

        assertEquals(order, expectedOrder);
    }

    /**
     * Tests correct path is found by greedy search for BNB.
     */
    @Test
    public void searchGreedyBNB() {
        BayesianNetwork bn = network.BNB;
        oc = new OrderChoice(bn, "M");
        String[] order = oc.search("GREEDY");
        String[] expectedOrder = {"J", "N", "O", "L", "K"};

        assertEquals(order, expectedOrder);
    }

    /**
     * Tests correct path is found by max cardinality search for BNC.
     */
    @Test
    public void searchMaxBNC() {
        BayesianNetwork bn = network.BNC;
        oc = new OrderChoice(bn, "P");
        String[] order = oc.search("MAX");
        String[] expectedOrder = {"U", "Z", "V", "S", "R", "Q"};

        assertEquals(order, expectedOrder);
    }

    /**
     * Tests correct path is found by greedy search for BNC.
     */
    @Test
    public void searchGreedyBNC() {
        BayesianNetwork bn = network.BNC;
        oc = new OrderChoice(bn, "S");
        String[] order = oc.search("GREEDY");
        String[] expectedOrder = {"P", "U", "Z", "R", "Q", "V"};

        assertEquals(order, expectedOrder);
    }

    /**
     * Tests that duplicated parent Nodes are reduced to unique Nodes only.
     */
    @Test
    public void testDeDupeParentList() {
        BayesianNetwork bn = network.BNC;
        oc = new OrderChoice(bn, "S");
        Node B = new Node("B", null);
        Node A = new Node("A", new ArrayList<>(Arrays.asList(B, B, B)));
        ArrayList<Node> dupedParents = new ArrayList<>(List.of(A));
        ArrayList<Node> unique = new ArrayList<>(List.of(B));

        oc.dedupeParentLists(dupedParents);
        assertEquals(A.getParents(), unique);
    }

    /**
     * Tests that the correct Node with the maximum number of marked neighbours is found.
     */
    @Test
    public void testGetMaxMarkedNeighboursNode() {
        BayesianNetwork bn = network.BNC;
        oc = new OrderChoice(bn, "A");
        ArrayList<Node> nodes = bn.getNodes();
        Node maxMarkedNeighbours = oc.getMostMarkedNeighboursNode(nodes, new ArrayList<>());
        assertEquals(maxMarkedNeighbours, bn.getNode("P"));
    }

    /**
     * Tests that correct Node with the minimum number of neighbours is found.
     */
    @Test
    public void testGetMinNeighboursNode() {
        BayesianNetwork bn = network.BNC;
        oc = new OrderChoice(bn, "A");
        ArrayList<Node> nodes = bn.getNodes();
        Node minNeighbours = oc.getMinNeighboursNode(nodes);
        assertEquals(minNeighbours, bn.getNode("P"));
    }

    /**
     * Checks that order processing is done correctly and returned as expected for greedy search.
     */
    @Test
    public void testProcessOrderGREED() {
        BayesianNetwork bn = network.BNA;
        oc = new OrderChoice(bn, "A");
        ArrayList<Node> nodes = bn.getNodes();

        String[] newOrder = oc.processOrder(nodes, "B", "GREED");
        String[] expectedOrder = {"A", "C", "D"};

        assertEquals(newOrder, expectedOrder);
    }

    /**
     * Checks that order processing is done correctly and returned as expected for max cardinality search.
     */
    @Test
    public void testProcessOrderMAX() {
        BayesianNetwork bn = network.BNA;
        oc = new OrderChoice(bn, "A");
        ArrayList<Node> nodes = bn.getNodes();

        String[] newOrder = oc.processOrder(nodes, "B", "MAX");
        String[] expectedOrder = {"D", "C", "A"};

        assertEquals(newOrder, expectedOrder);
    }

    /**
     * Test that suitable parent links are in place when we convert a Bayesian Network to an undirected graph.
     */
    @Test
    public void testCreateUndirectedGraph() {
        BayesianNetwork bn = network.BNC;
        oc = new OrderChoice(bn, "P");

        Node R = bn.getNode("R");
        Node Q = bn.getNode("Q");
        Node V = bn.getNode("V");
        Node S = bn.getNode("S");
        Node U = bn.getNode("U");

        ArrayList<Node> parentsR = R.getParents();
        ArrayList<Node> parentsQ = Q.getParents();
        ArrayList<Node> parentsV = V.getParents();
        ArrayList<Node> parentsS = S.getParents();
        ArrayList<Node> parentsU = U.getParents();

        assertTrue(parentsR.contains(Q));
        assertTrue(parentsQ.contains(R));
        assertTrue(parentsV.contains(S));
        assertTrue(parentsS.contains(V));
        assertTrue(parentsU.contains(S));
    }

    /**
     * Checks that for Nodes with multiple, unconnected parents, when addLinksBetweenParents is run, parents are then linked.
     */
    @Test
    public void addLinksBetweenParents() {
        BayesianNetwork bn = network.BNA;
        oc = new OrderChoice(bn, "A");

        Node first = new Node("A", null);
        Node second = new Node("B", null);
        Node third = new Node("C", new ArrayList<>(List.of(first, second)));
        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(first, second, third));

        oc.addLinksBetweenParents(nodes);
        ArrayList<Node> firstParents = first.getParents();
        ArrayList<Node> secondParents = second.getParents();

        assertTrue(firstParents.contains(second));
        assertTrue(secondParents.contains(first));
    }

    /**
     * Checks the check which checks whether two Nodes are parents of each other.
     */
    @Test
    public void testAreParentsConnected(){
        BayesianNetwork bn = network.BNA;
        oc = new OrderChoice(bn, "A");

        Node first = new Node("A", null);
        Node second = new Node("B", new ArrayList<>(List.of(first)));
        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(first, second));

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(0);
        assertFalse(oc.areParentsConnected(nodes, set));
        oc.connectParents(nodes, set);
        assertTrue(oc.areParentsConnected(nodes, set));
    }

    /**
     * Connects two Nodes and then checks that they are parents of each other.
     */
    @Test
    public void testConnectParents() {
        BayesianNetwork bn = network.BNA;
        oc = new OrderChoice(bn, "A");

        Node first = new Node("A", null);
        Node second = new Node("B", new ArrayList<>(List.of(first)));
        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(first, second));

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(0);
        oc.connectParents(nodes, set);

        ArrayList<Node> firstParents = first.getParents();
        ArrayList<Node> secondParents = second.getParents();

        assertTrue(firstParents.contains(second));
        assertTrue(secondParents.contains(first));
    }

    /**
     * Tests that Nodes are correctly converted from a set to ArrayList based on combination provided.
     */
    @Test
    public void testGetParentComboFromSet() {
        BayesianNetwork bn = network.BNA;
        oc = new OrderChoice(bn, "A");

        Node first = new Node("A", null);
        Node second = new Node("B", new ArrayList<>(List.of(first)));
        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(first, second));

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(0);

        ArrayList<Node> combo = oc.getParentComboFromSet(nodes, set);
        assertEquals(combo.get(0), first);
        assertEquals(combo.get(1), second);
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
