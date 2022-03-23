package tests;

import org.junit.Test;
import support.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VariableEliminationTest {

    Network network = new Network();
    VariableElimination varElim;

    @Test
    public void eliminate() {
    }

    @Test
    public void prune() {
        varElim = new VariableElimination();
        BayesianNetwork bn = network.BNC;
        String[] initialOrder = getInitialOrder();
        String[] pruneOutput = varElim.prune(bn, "U", initialOrder);

        assertEquals(pruneOutput, getOutputOrder());
    }

    @Test
    public void createFactors() {
        varElim = new VariableElimination();
        BayesianNetwork bn = network.BNC;
        String[] order = getOutputOrder();
        ArrayList<Factor> factors = varElim.createFactors(bn, "U", order);
        assertEquals(factors.size(), 5);
        Factor pFactor = factors.get(1);
        assertTrue(pFactor.getNodeLabels().contains("P"));
    }

    @Test
    public void getRelatedFactors() {
        varElim = new VariableElimination();
        BayesianNetwork bn = network.BNC;
        String[] order = getOutputOrder();
        ArrayList<Factor> factors = varElim.createFactors(bn, "U", order);
        ArrayList<Factor> relatedFactors = varElim.getRelatedFactors(factors, "R");
        assertEquals(relatedFactors.size(), 2);
        assertTrue(relatedFactors.get(1).getNodeLabels().contains("Q"));
    }

    @Test
    public void testFindCommonLabels() {
        varElim = new VariableElimination();
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> common = varElim.findCommonLabels(f1, f2);

        assertEquals(common, new ArrayList<>(List.of("C")));
    }

    @Test
    public void testFirstOnlyLabels() {
        varElim = new VariableElimination();
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> firstOnly = varElim.findFirstOnlyLabels(f1, f2);

        assertEquals(firstOnly, new ArrayList<>(List.of("A", "B")));
    }

    @Test
    public void testSecondOnlyLabels() {
        varElim = new VariableElimination();
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> secondOnly = varElim.findSecondOnlyLabels(f1, f2);

        assertEquals(secondOnly, new ArrayList<>(List.of("D", "E")));
    }

    @Test
    public void testGetAllLabels() {
        varElim = new VariableElimination();
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> all = varElim.getAllLabels(f1, f2);

        assertEquals(all, new ArrayList<>(List.of("C", "A", "B", "D", "E")));
    }

    @Test
    public void testGetV1V2() {
        varElim = new VariableElimination();
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> v1v2 = varElim.getV1V2(f1, f2);

        assertEquals(v1v2, new ArrayList<>(List.of("C", "A", "B")));
    }

    @Test
    public void testGetV1V3() {
        varElim = new VariableElimination();
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> v1v3 = varElim.getV1V3(f1, f2);

        assertEquals(v1v3, new ArrayList<>(List.of("C", "D", "E")));
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
        varElim = new VariableElimination();
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
}
