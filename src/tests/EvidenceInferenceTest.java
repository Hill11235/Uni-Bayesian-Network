package tests;

import org.junit.Before;
import org.junit.Test;
import support.BayesianNetwork;
import support.EvidenceInference;
import support.Factor;
import support.Network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests all the underlying methods for solving a query given evidence.
 */
public class EvidenceInferenceTest {

    Network network = new Network();
    EvidenceInference infer;

    /**
     * Set up fresh SimpleInference object before each test.
     */
    @Before
    public void setUp() {
        infer = new EvidenceInference();
    }

    @Test
    public void testPrune1() {
        BayesianNetwork bn = network.BNC;
        String[] initialOrder = getInitialOrder();
        String[] pruneOutput = infer.prune(bn, "U", initialOrder, getEvidence1());

        assertEquals(pruneOutput, getOutputOrder1());
    }

    @Test
    public void testPrune2() {
        BayesianNetwork bn = network.BNC;
        String[] initialOrder = getInitialOrder();
        String[] pruneOutput = infer.prune(bn, "Q", initialOrder, getEvidence2());

        assertEquals(pruneOutput, getOutputOrder2());
    }

    @Test
    public void testIsIntersectionEmpty() {
        ArrayList<String> list1 = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList("D", "E", "F", "G"));
        ArrayList<String> list3 = new ArrayList<>(Arrays.asList("H", "I", "J", "K"));

        assertFalse(infer.isIntersectionEmpty(list1, list2));
        assertTrue(infer.isIntersectionEmpty(list1, list3));
    }

    @Test
    public void testProjectEvidence() {
        //create list of Factors
        ArrayList<Factor> factors = getFactors();

        //arraylist of string[] for evidence
        ArrayList<String[]> evidence = getEvidence4();

        ArrayList<Factor> updatedFactors = infer.projectEvidence(factors, evidence);
        Factor updatedF1 = updatedFactors.get(0);
        Factor updatedF2 = updatedFactors.get(1);

        HashMap<String, Double> f1Probs = updatedF1.getProbabilities();
        HashMap<String, Double> f2Probs = updatedF2.getProbabilities();

        assertEquals(f1Probs.get("000"), 0.0, 0.0001);
        assertEquals(f1Probs.get("001"), 0.0, 0.0001);
        assertEquals(f1Probs.get("010"), 1.0, 0.0001);
        assertEquals(f1Probs.get("011"), 1.0, 0.0001);
        assertEquals(f1Probs.get("100"), 0.0, 0.0001);
        assertEquals(f1Probs.get("101"), 0.0, 0.0001);
        assertEquals(f1Probs.get("110"), 1.0, 0.0001);
        assertEquals(f1Probs.get("111"), 1.0, 0.0001);

        assertEquals(f2Probs.get("00"), 0.0, 0.0001);
        assertEquals(f2Probs.get("01"), 0.0, 0.0001);
        assertEquals(f2Probs.get("10"), 2.0, 0.0001);
        assertEquals(f2Probs.get("11"), 2.0, 0.0001);

    }

    private ArrayList<Factor> getFactors() {
        ArrayList<String> labels1 = new ArrayList<>(List.of("K", "P", "Z"));
        Factor f1 = new Factor(labels1);
        ArrayList<String> labels2 = new ArrayList<>(List.of("P", "Q"));
        Factor f2 = new Factor(labels2);
        f1.addProbabilities(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
        f2.addProbabilities(2.0, 2.0, 2.0, 2.0);

        ArrayList<Factor> factors = new ArrayList<>();
        factors.add(f1);
        factors.add(f2);
        return factors;
    }

    @Test
    public void testFactorContainsVariable() {
        ArrayList<String> labels1 = new ArrayList<>(List.of("K", "Z"));
        Factor f1 = new Factor(labels1);
        ArrayList<String> labels2 = new ArrayList<>(List.of("P", "Q"));
        Factor f2 = new Factor(labels2);

        ArrayList<String[]> evidenceList = getEvidence1();
        String[] evidence = evidenceList.get(0);

        assertTrue(infer.factorContainsVariable(f1, evidence));
        assertFalse(infer.factorContainsVariable(f2, evidence));
    }

    @Test
    public void testSetProbability1() {
        ArrayList<String> labels1 = new ArrayList<>(List.of("K", "Z"));
        Factor f1 = new Factor(labels1);
        f1.addProbabilities(0.05, 0.95, 0.4, 0.6);

        ArrayList<String[]> evidenceList = getEvidence1();
        String[] evidence = evidenceList.get(0);
        infer.setProbability(f1, evidence);

        HashMap<String, Double> cpt = f1.getProbabilities();

        assertEquals(cpt.get("00"), 0.0, 0.0001);
        assertEquals(cpt.get("01"), 0.95, 0.0001);
        assertEquals(cpt.get("10"), 0.0, 0.0001);
        assertEquals(cpt.get("11"), 0.6, 0.0001);
    }

    @Test
    public void testSetProbability2() {
        ArrayList<String> labels1 = new ArrayList<>(List.of("K", "Z"));
        Factor f1 = new Factor(labels1);
        f1.addProbabilities(0.05, 0.95, 0.4, 0.6);

        ArrayList<String[]> evidenceList = getEvidence3();
        String[] evidence = evidenceList.get(0);
        infer.setProbability(f1, evidence);

        HashMap<String, Double> cpt = f1.getProbabilities();

        assertEquals(cpt.get("00"), 0.05, 0.0001);
        assertEquals(cpt.get("01"), 0.0, 0.0001);
        assertEquals(cpt.get("10"), 0.4, 0.0001);
        assertEquals(cpt.get("11"), 0.0, 0.0001);
    }

    @Test
    public void testGetValue() {
        ArrayList<String> labels1 = new ArrayList<>(List.of("K"));
        Factor f1 = new Factor(labels1);
        f1.addProbabilities(0.29, 0.71);

        Factor f2 = new Factor(labels1);
        f2.addProbabilities(0.6725, 0.3275);

        ArrayList<Factor> factors = new ArrayList<>(Arrays.asList(f1, f2));
        assertEquals(infer.getValue(factors, "F"), 0.45615, 0.0001);

        ArrayList<Factor> factors1 = new ArrayList<>(Arrays.asList(f1, f2));
        assertEquals(infer.getValue(factors1, "T"), 0.54385, 0.0001);
    }

    @Test
    public void testNormalise() {
        ArrayList<String> labels1 = new ArrayList<>(List.of("K"));
        Factor f1 = new Factor(labels1);
        f1.addProbabilities(0.195025, 0.232525);
        HashMap<String, Double> cptBefore = f1.getProbabilities();

        infer.normalise(f1);
        HashMap<String, Double> cpt = f1.getProbabilities();

        assertEquals(cpt.get("0"), 0.45615, 0.0001);
        assertEquals(cpt.get("1"), 0.54385, 0.0001);
    }

    @Test
    public void testGetSumOfProbabilities() {
        ArrayList<String> labels1 = new ArrayList<>(List.of("K"));
        Factor f1 = new Factor(labels1);
        f1.addProbabilities(0.232525, 0.195025);
        HashMap<String, Double> cpt = f1.getProbabilities();

        assertEquals(infer.getSumOfProbabilities(cpt), 0.42755, 0.000001);
    }

    private ArrayList<String[]> getEvidence1() {
        String[] evidenceArray = {"Z", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray);

        return evidence;
    }

    private ArrayList<String[]> getEvidence2() {
        String[] evidenceArray = {"R", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray);

        return evidence;
    }

    private ArrayList<String[]> getEvidence3() {
        String[] evidenceArray = {"Z", "F"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray);

        return evidence;
    }

    private ArrayList<String[]> getEvidence4() {
        String[] evidenceArray = {"P", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray);
        return evidence;
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

    private String[] getOutputOrder1() {
        String[] outputOrder = new String[6];
        outputOrder[0] = "P";
        outputOrder[1] = "Q";
        outputOrder[2] = "R";
        outputOrder[3] = "S";
        outputOrder[4] = "V";
        outputOrder[5] = "Z";

        return outputOrder;
    }

    private String[] getOutputOrder2() {
        String[] outputOrder = new String[1];
        outputOrder[0] = "P";

        return outputOrder;
    }
}
