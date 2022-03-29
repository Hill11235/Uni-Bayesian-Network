package tests;

import org.junit.Before;
import org.junit.Test;
import support.BayesianNetwork;
import logic.EvidenceInference;
import support.Network;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Used to test full inference with evidence as per stacscheck and with some additional tests.
 */
public class P3Tests {

    Network network = new Network();
    EvidenceInference infer;
    BayesianNetwork BNA;
    BayesianNetwork BNB;
    BayesianNetwork BNC;
    BayesianNetwork BND;
    BayesianNetwork CNX;

    /**
     * Set up EvidenceInference object and networks before each test.
     */
    @Before
    public void setUp() {
        infer = new EvidenceInference();
        BNA = network.BNA;
        BNB = network.BNB;
        BNC = network.BNC;
        BND = network.BND;
        CNX = network.CNX;
    }

    /**
     * Testing a diagnostic query on CNX. P(Attack|Alert,Anomalous log).
     */
    @Test
    public void CNXTest1() {
        String queryVariable = "A";
        String value = "T";
        String[] order = {"I", "E", "M", "B", "H", "W", "L", "A"};
        String[] evidenceArray1 = {"E", "T"};
        String[] evidenceArray2 = {"L", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);
        evidence.add(evidenceArray2);

        double answer = infer.eliminate(CNX, queryVariable, value, order, evidence);
        assertEquals(answer, 0.84109, 0.0001);
    }

    /**
     * Testing a predictive query on CNX. P(Maintenance|Attack, ~Alert).
     */
    @Test
    public void CNXTest2() {
        String queryVariable = "M";
        String value = "T";
        String[] order = {"I", "E", "M", "B", "H", "W", "L", "A"};
        String[] evidenceArray1 = {"A", "T"};
        String[] evidenceArray2 = {"E", "F"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);
        evidence.add(evidenceArray2);

        double answer = infer.eliminate(CNX, queryVariable, value, order, evidence);
        assertEquals(answer, 0.97481, 0.0001);
    }

    /**
     * Testing a query on BND. P(Winter|On time).
     */
    @Test
    public void BNDTest1() {
        String queryVariable = "W";
        String value = "T";
        String[] order = {"W", "S", "E", "D", "R", "B", "L"};
        String[] evidenceArray1 = {"L", "F"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);

        double answer = infer.eliminate(BND, queryVariable, value, order, evidence);
        assertEquals(answer, 0.22787, 0.0001);
    }

    /**
     * Testing a query on BND. P(On time|bus delay, storm).
     */
    @Test
    public void BNDTest2() {
        String queryVariable = "L";
        String value = "F";
        String[] order = {"W", "S", "E", "D", "R", "B", "L"};
        String[] evidenceArray1 = {"B", "T"};
        String[] evidenceArray2 = {"S", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);
        evidence.add(evidenceArray2);

        double answer = infer.eliminate(BND, queryVariable, value, order, evidence);
        assertEquals(answer, 0.11875, 0.000001);
    }

    /**
     * Stacscheck test for BNA.
     */
    @Test
    public void BNATest1() {
        String queryVariable = "D";
        String value = "T";
        String[] order = {"A", "B", "C"};
        String[] evidenceArray1 = {"A", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);

        double answer = infer.eliminate(BNA, queryVariable, value, order, evidence);
        assertEquals(answer, 0.54200, 0.000001);
    }

    /**
     * Stacscheck test for BNA.
     */
    @Test
    public void BNATest2() {
        String queryVariable = "D";
        String value = "T";
        String[] order = {"A", "B", "C"};
        String[] evidenceArray1 = {"A", "T"};
        String[] evidenceArray2 = {"B", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);
        evidence.add(evidenceArray2);

        double answer = infer.eliminate(BNA, queryVariable, value, order, evidence);
        assertEquals(answer, 0.58, 0.000001);
    }

    /**
     * Stacscheck test for BNA.
     */
    @Test
    public void BNATest3() {
        String queryVariable = "A";
        String value = "T";
        String[] order = {"A", "B", "C"};
        String[] evidenceArray1 = {"C", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);

        double answer = infer.eliminate(BNA, queryVariable, value, order, evidence);
        assertEquals(answer, 0.09831, 0.00001);
    }

    /**
     * Stacscheck test for BNA.
     */
    @Test
    public void BNATest4() {
        String queryVariable = "D";
        String value = "T";
        String[] order = {"A", "B", "C"};
        String[] evidenceArray1 = {"B", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);

        double answer = infer.eliminate(BNA, queryVariable, value, order, evidence);
        assertEquals(answer, 0.58, 0.00001);
    }

    /**
     * Stacscheck test for BNA.
     */
    @Test
    public void BNATest5() {
        String queryVariable = "D";
        String value = "T";
        String[] order = {"A", "B", "C"};
        String[] evidenceArray1 = {"A", "F"};
        String[] evidenceArray2 = {"B", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);
        evidence.add(evidenceArray2);

        double answer = infer.eliminate(BNA, queryVariable, value, order, evidence);
        assertEquals(answer, 0.58, 0.000001);
    }

    /**
     * Stacscheck test for BNB.
     */
    @Test
    public void BNBTest1() {
        String queryVariable = "K";
        String value = "T";
        String[] order = {"O", "J", "M", "L", "N"};
        String[] evidenceArray1 = {"O", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);

        double answer = infer.eliminate(BNB, queryVariable, value, order, evidence);
        assertEquals(answer, 0.54385, 0.0001);
    }

    /**
     * Stacscheck test for BNB.
     */
    @Test
    public void BNBTest2() {
        String queryVariable = "J";
        String value = "T";
        String[] order = {"O", "M", "L", "K", "N"};
        String[] evidenceArray1 = {"O", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);

        double answer = infer.eliminate(BNB, queryVariable, value, order, evidence);
        assertEquals(answer, 0.04233, 0.0001);
    }

    /**
     * Stacscheck test for BNB.
     */
    @Test
    public void BNBTest3() {
        String queryVariable = "N";
        String value = "T";
        String[] order = {"J", "L", "K", "M", "O"};
        String[] evidenceArray1 = {"J", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);

        double answer = infer.eliminate(BNB, queryVariable, value, order, evidence);
        assertEquals(answer, 0.43360, 0.0001);
    }

    /**
     * Stacscheck test for BNB.
     */
    @Test
    public void BNBTest4() {
        String queryVariable = "N";
        String value = "T";
        String[] order = {"J", "L", "K", "M", "O"};
        String[] evidenceArray1 = {"J", "T"};
        String[] evidenceArray2 = {"L", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);
        evidence.add(evidenceArray2);

        double answer = infer.eliminate(BNB, queryVariable, value, order, evidence);
        assertEquals(answer, 0.42400, 0.0001);
    }

    /**
     * Stacscheck test for BNB.
     */
    @Test
    public void BNBTest5() {
        String queryVariable = "N";
        String value = "T";
        String[] order = {"J", "L", "K", "M", "O"};
        String[] evidenceArray1 = {"J", "T"};
        String[] evidenceArray2 = {"L", "F"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);
        evidence.add(evidenceArray2);

        double answer = infer.eliminate(BNB, queryVariable, value, order, evidence);
        assertEquals(answer, 0.45600, 0.0001);
    }

    /**
     * Additional test for BNB from lectures.
     */
    @Test
    public void BNBTest6() {
        String queryVariable = "L";
        String value = "T";
        String[] order = {"K", "M", "J", "N", "O"};
        String[] evidenceArray1 = {"K", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);

        double answer = infer.eliminate(BNB, queryVariable, value, order, evidence);
        assertEquals(answer, 0.7, 0.0001);
    }

    /**
     * Stacscheck test for BNC.
     */
    @Test
    public void BNCTest1() {
        String queryVariable = "Z";
        String value = "T";
        String[] order = {"P", "U", "R", "S", "Q", "V"};
        String[] evidenceArray1 = {"R", "T"};
        String[] evidenceArray2 = {"U", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);
        evidence.add(evidenceArray2);

        double answer = infer.eliminate(BNC, queryVariable, value, order, evidence);
        assertEquals(answer, 0.43368, 0.0001);
    }

    /**
     * Stacscheck test for BNC.
     */
    @Test
    public void BNCTest2() {
        String queryVariable = "P";
        String value = "T";
        String[] order = {"U", "R", "Z", "S", "Q", "V"};
        String[] evidenceArray1 = {"Z", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);

        double answer = infer.eliminate(BNC, queryVariable, value, order, evidence);
        assertEquals(answer, 0.05509, 0.0001);
    }

    /**
     * Stacscheck test for BNC.
     */
    @Test
    public void BNCTest3() {
        String queryVariable = "Q";
        String value = "T";
        String[] order = {"P", "U", "R", "Z", "S", "V"};
        String[] evidenceArray1 = {"Z", "T"};
        String[] evidenceArray2 = {"S", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);
        evidence.add(evidenceArray2);

        double answer = infer.eliminate(BNC, queryVariable, value, order, evidence);
        assertEquals(answer, 0.92141, 0.0001);
    }

    /**
     * Stacscheck test for BNC.
     */
    @Test
    public void BNCTest4() {
        String queryVariable = "U";
        String value = "T";
        String[] order = {"P", "R", "Z", "S", "Q", "V"};
        String[] evidenceArray1 = {"Z", "T"};
        String[] evidenceArray2 = {"Q", "T"};
        String[] evidenceArray3 = {"R", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);
        evidence.add(evidenceArray2);
        evidence.add(evidenceArray3);

        double answer = infer.eliminate(BNC, queryVariable, value, order, evidence);
        assertEquals(answer, 0.34204, 0.00001);
    }

    /**
     * Own test to check the converse of the previous test.
     */
    @Test
    public void converseBNCTest4() {
        String queryVariable = "U";
        String value = "F";
        String[] order = {"P", "R", "Z", "S", "Q", "V"};
        String[] evidenceArray1 = {"Z", "T"};
        String[] evidenceArray2 = {"Q", "T"};
        String[] evidenceArray3 = {"R", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);
        evidence.add(evidenceArray2);
        evidence.add(evidenceArray3);

        double answer = infer.eliminate(BNC, queryVariable, value, order, evidence);
        assertEquals(answer, 1 - 0.34204, 0.00001);
    }

    /**
     * Check that for a Node with no parents and no evidence provided the correct answer is returned.
     */
    @Test
    public void testParentlessNodeNoEvidence() {
        String queryVariable = "P";
        String value = "F";
        String[] order = {"P", "R", "Z", "S", "Q", "V"};
        ArrayList<String[]> evidence = new ArrayList<>();

        double answer = infer.eliminate(BNC, queryVariable, value, order, evidence);
        assertEquals(answer, 0.95, 0.00001);
    }
}
