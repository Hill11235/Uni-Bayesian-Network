package tests;

import org.junit.Before;
import org.junit.Test;
import support.BayesianNetwork;
import support.Factor;
import support.Network;
import support.SimpleInference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests all the underlying methods for solving a simple query.
 */
public class SimpleInferenceTest {

    Network network = new Network();
    SimpleInference infer;

    /**
     * Set up fresh SimpleInference object before each test.
     */
    @Before
    public void setUp() {
        infer = new SimpleInference();
    }

    @Test
    public void prune() {
        BayesianNetwork bn = network.BNC;
        String[] initialOrder = getInitialOrder();
        String[] pruneOutput = infer.prune(bn, "U", initialOrder, null);

        assertEquals(pruneOutput, getOutputOrder());
    }

    @Test
    public void createFactors() {
        BayesianNetwork bn = network.BNC;
        String[] order = getOutputOrder();
        ArrayList<Factor> factors = infer.createFactors(bn, "U", order);
        assertEquals(factors.size(), 5);
        Factor pFactor = factors.get(1);
        assertTrue(pFactor.getNodeLabels().contains("P"));
    }

    @Test
    public void getRelatedFactors() {
        BayesianNetwork bn = network.BNC;
        String[] order = getOutputOrder();
        ArrayList<Factor> factors = infer.createFactors(bn, "U", order);
        ArrayList<Factor> relatedFactors = infer.getRelatedFactors(factors, "R");
        assertEquals(relatedFactors.size(), 2);
        assertTrue(relatedFactors.get(1).getNodeLabels().contains("Q"));
    }

    @Test
    public void testFindCommonLabels() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> common = infer.findCommonLabels(f1, f2);

        assertEquals(common, new ArrayList<>(List.of("C")));
    }

    @Test
    public void testFirstOnlyLabels() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> firstOnly = infer.findFirstOnlyLabels(f1, f2);

        assertEquals(firstOnly, new ArrayList<>(List.of("A", "B")));
    }

    @Test
    public void testSecondOnlyLabels() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> secondOnly = infer.findSecondOnlyLabels(f1, f2);

        assertEquals(secondOnly, new ArrayList<>(List.of("D", "E")));
    }

    @Test
    public void testGetAllLabels() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> all = infer.getAllLabels(f1, f2);

        assertEquals(all, new ArrayList<>(List.of("C", "A", "B", "D", "E")));
    }

    @Test
    public void testGetV1V2() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> v1v2 = infer.getV1V2(f1, f2);

        assertEquals(v1v2, new ArrayList<>(List.of("C", "A", "B")));
    }

    @Test
    public void testGetV1V3() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("C", "D", "E"));
        Factor f1 = new Factor(labels1);
        Factor f2 = new Factor(labels2);
        ArrayList<String> v1v3 = infer.getV1V3(f1, f2);

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
        Double prob = infer.getProbFromFactor(labelMapping, f1);

        assertEquals(prob, correctMap.get("011"));
    }

    @Test
    public void testJoin1() {
        ArrayList<String> labels1 = new ArrayList<>(List.of("A"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("A", "B"));
        Factor f1 = new Factor(labels1);
        f1.addProbabilities(0.95, 0.05);
        Factor f2 = new Factor(labels2);
        f2.addProbabilities(0.2, 0.8, 0.95, 0.05);

        Factor f3 = infer.join(f1, f2);
        HashMap<String, Double> joinedCPT = f3.getProbabilities();

        assertEquals(joinedCPT.get("00"), 0.19, 0.0001);
        assertEquals(joinedCPT.get("01"), 0.76, 0.0001);
        assertEquals(joinedCPT.get("10"), 0.0475, 0.0001);
        assertEquals(joinedCPT.get("11"), 0.0025, 0.0001);
    }

    @Test
    public void testJoin2() {
        ArrayList<String> labels1 = new ArrayList<>(List.of("A", "B"));
        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("A", "C"));
        Factor f1 = new Factor(labels1);
        f1.addProbabilities(0.19, 0.76, 0.0475, 0.0025);
        Factor f2 = new Factor(labels2);
        f2.addProbabilities(0.25, 0.75, 0.85, 0.15);

        Factor f3 = infer.join(f1, f2);
        HashMap<String, Double> joinedCPT = f3.getProbabilities();

        assertEquals(joinedCPT.get("000"), 0.0475, 0.000001);
        assertEquals(joinedCPT.get("001"), 0.1425, 0.000001);
        assertEquals(joinedCPT.get("010"), 0.19, 0.000001);
        assertEquals(joinedCPT.get("011"), 0.57, 0.000001);
        assertEquals(joinedCPT.get("100"), 0.040375, 0.000001);
        assertEquals(joinedCPT.get("101"), 0.007125, 0.000001);
        assertEquals(joinedCPT.get("110"), 0.002125, 0.000001);
        assertEquals(joinedCPT.get("111"), 0.000375, 0.000001);
    }

    @Test
    public void testCheckMatchTrue() {
        String key = "0110";
        HashMap<Integer, String> positionMapping = new HashMap<>();
        positionMapping.put(0, "0");
        positionMapping.put(2, "1");
        positionMapping.put(3, "0");

        assertTrue(infer.checkMatch(key, positionMapping));
    }

    @Test
    public void testCheckMatchFalse() {
        String key = "011011";
        HashMap<Integer, String> positionMapping = new HashMap<>();
        positionMapping.put(0, "0");
        positionMapping.put(2, "1");
        positionMapping.put(3, "0");
        positionMapping.put(4, "1");
        positionMapping.put(5, "0");

        assertFalse(infer.checkMatch(key, positionMapping));
    }

    @Test
    public void testGetPositionMapping() {
        ArrayList<String> labels = new ArrayList<>(Arrays.asList("A", "B", "C"));
        Factor f1 = new Factor(labels);

        HashMap<String, String> labelMapping = new HashMap<>();
        labelMapping.put("B", "1");
        labelMapping.put("A", "1");
        labelMapping.put("C", "1");

        HashMap<Integer, String> expectedMapping = new HashMap<>();
        expectedMapping.put(1, "1");
        expectedMapping.put(0, "1");
        expectedMapping.put(2, "1");

        HashMap<Integer, String> positionMapping = infer.getPositionMapping(labelMapping, f1);
        assertEquals(positionMapping, expectedMapping);
    }

    @Test
    public void testMarginalise() {
        ArrayList<String> labels = new ArrayList<>(Arrays.asList("A", "B", "C"));
        Factor f1 = new Factor(labels);
        f1.addProbabilities(0.0475, 0.1425, 0.19, 0.57, 0.040375, 0.007125, 0.002125, 0.000375);

        Factor reducedFactor = infer.marginalise(f1, "A");
        HashMap<String, Double> reducedCPT = reducedFactor.getProbabilities();
        assertEquals(reducedCPT.get("00"), 0.087875, 0.000001);
        assertEquals(reducedCPT.get("01"), 0.149625, 0.000001);
        assertEquals(reducedCPT.get("10"), 0.192125, 0.000001);
        assertEquals(reducedCPT.get("11"), 0.570375, 0.000001);
    }

    @Test
    public void joinMarginalise() {
        ArrayList<String> labels1 = new ArrayList<>(Arrays.asList("A"));
        Factor f1 = new Factor(labels1);
        f1.addProbabilities(0.95, 0.05);

        ArrayList<String> labels2 = new ArrayList<>(Arrays.asList("A", "B"));
        Factor f2 = new Factor(labels2);
        f2.addProbabilities(0.2, 0.8, 0.95, 0.05);

        ArrayList<String> labels3 = new ArrayList<>(Arrays.asList("A", "C"));
        Factor f3 = new Factor(labels3);
        f3.addProbabilities(0.25, 0.75, 0.85, 0.15);

        ArrayList<Factor> factors = new ArrayList<>(Arrays.asList(f1, f2, f3));
        Factor jmFactor = infer.joinMarginalise(factors, "A");
        HashMap<String, Double> probs = jmFactor.getProbabilities();

        assertEquals(probs.get("00"), 0.087875, 0.000001);
        assertEquals(probs.get("01"), 0.192125, 0.000001);
        assertEquals(probs.get("10"), 0.149625, 0.000001);
        assertEquals(probs.get("11"), 0.570375, 0.000001);
    }

    @Test
    public void getValue() {
        Factor cpt = new Factor(new ArrayList<>(List.of("A")));
        ArrayList<Factor> factors = new ArrayList<>(List.of(cpt));
        cpt.addProbabilities(0.05, 0.95);
        assertEquals(infer.getValue(factors, "T"), 0.95, 0.0001);
        assertEquals(infer.getValue(factors, "F"), 0.05, 0.0001);
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
