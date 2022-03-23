package tests;

import org.junit.Before;
import org.junit.Test;
import support.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VariableEliminationTest {

    Network network = new Network();
    VariableElimination varElim;

    @Before
    public void setUp() {
        varElim = new VariableElimination();
    }

    @Test
    public void eliminate() {
    }

    @Test
    public void prune() {
        BayesianNetwork bn = network.BNC;
        String[] initialOrder = getInitialOrder();
        String[] pruneOutput = varElim.prune(bn, "U", initialOrder);

        assertEquals(pruneOutput, getOutputOrder());
    }

    @Test
    public void createFactors() {
        BayesianNetwork bn = network.BNC;
        String[] order = getOutputOrder();
        ArrayList<Factor> factors = varElim.createFactors(bn, "U", order);
        assertEquals(factors.size(), 5);
        Factor pFactor = factors.get(1);
        assertTrue(pFactor.getNodeLabels().contains("P"));
    }

    @Test
    public void getRelatedFactors() {
        BayesianNetwork bn = network.BNC;
        String[] order = getOutputOrder();
        ArrayList<Factor> factors = varElim.createFactors(bn, "U", order);
        ArrayList<Factor> relatedFactors = varElim.getRelatedFactors(factors, "R");
        assertEquals(relatedFactors.size(), 2);
        assertTrue(relatedFactors.get(1).getNodeLabels().contains("Q"));
    }

    @Test
    public void testFindCommonLabels() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> common = varElim.findCommonLabels(f1, f2);

        assertEquals(common, new ArrayList<>(List.of("C")));
    }

    @Test
    public void testFirstOnlyLabels() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> firstOnly = varElim.findFirstOnlyLabels(f1, f2);

        assertEquals(firstOnly, new ArrayList<>(List.of("A", "B")));
    }

    @Test
    public void testSecondOnlyLabels() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> secondOnly = varElim.findSecondOnlyLabels(f1, f2);

        assertEquals(secondOnly, new ArrayList<>(List.of("D", "E")));
    }

    @Test
    public void testGetAllLabels() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> all = varElim.getAllLabels(f1, f2);

        assertEquals(all, new ArrayList<>(List.of("C", "A", "B", "D", "E")));
    }

    @Test
    public void testGetV1V2() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> v1v2 = varElim.getV1V2(f1, f2);

        assertEquals(v1v2, new ArrayList<>(List.of("C", "A", "B")));
    }

    @Test
    public void testGetV1V3() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> v1v3 = varElim.getV1V3(f1, f2);

        assertEquals(v1v3, new ArrayList<>(List.of("C", "D", "E")));
    }

    @Test
    public void testGetProbFromFactor() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        Factor f1 = new Factor(labels1);
        HashMap<String, Double> correctMap = addValidProb(f1);
        HashMap<String, String> labelMapping = new HashMap<>();
        labelMapping.put("B", "1");
        labelMapping.put("C", "1");
        labelMapping.put("A", "0");
        Double prob = varElim.getProbFromFactor(labelMapping, f1);

        assertEquals(prob, correctMap.get("011"));
    }

    @Test
    public void testJoinOneChild() {
        //use example in notes and loop through resultant factor probabilities to compare.
        //f(a)
        //f(a,b)
        //combine using join
        //compare vs correct f3 factor

    }

    @Test
    public void testJoinTwoChildren() {
        //use example in notes and loop through resultant factor probabilities to compare.

    }

    @Test
    public void testMarginalise() {
        //use example in notes and loop through resultant factor probabilities to compare.

    }

    @Test
    public void joinMarginalise() {
    }

    @Test
    public void getValue() {
        Factor cpt = new Factor(new ArrayList<>(List.of("A")));
        ArrayList<Factor> factors = new ArrayList<>(List.of(cpt));
        cpt.addProbabilities(0.05, 0.95);
        assertEquals(varElim.getValue(factors, "T"), 0.95, 0.0001);
        assertEquals(varElim.getValue(factors, "F"), 0.05, 0.0001);
    }

    private String[] getInitialOrder() {
        String[] initialOrder = new String[7];
        initialOrder[0] = "P";
        initialOrder[1] = "Q";
        initialOrder[2] = "R";
        initialOrder[3] = "S";
        initialOrder[4] = "U";
        initialOrder[5] = "V";
        initialOrder[6] = "Z";

        return initialOrder;
    }

    private String[] getOutputOrder() {
        String[] outputOrder = new String[4];
        outputOrder[0] = "P";
        outputOrder[1] = "Q";
        outputOrder[2] = "R";
        outputOrder[3] = "S";

        return outputOrder;
    }

    /**
     * Helper method for adding probabilities.
     * @return list of probabilities associated with table.
     */
    private HashMap<String, Double> addValidProb(Factor fac) {
        Double p1 = 0.9;
        Double p2 = 0.1;
        Double p3 = 0.3;
        Double p4 = 0.7;
        Double p5 = 0.6;
        Double p6 = 0.4;
        Double p7 = 0.2;
        Double p8 = 0.8;

        HashMap<String, Double> output = new HashMap<>();
        output.put("000", p1);
        output.put("001", p2);
        output.put("010", p3);
        output.put("011", p4);
        output.put("100", p5);
        output.put("101", p6);
        output.put("110", p7);
        output.put("111", p8);

        fac.addProbabilities(p1, p2, p3, p4, p5, p6, p7, p8);
        return output;
    }
}
