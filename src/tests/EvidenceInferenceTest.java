package tests;

import org.junit.Before;
import org.junit.Test;
import support.*;

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
}
