package tests;

import org.junit.Before;
import org.junit.Test;
import support.BayesianNetwork;
import logic.EvidenceInference;
import support.Network;
import logic.OrderChoice;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Used to test full inference with order finding first. Same tests as P3 but order determined for each first.
 */
public class P4Tests {

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
     * Testing a query on CNX. P(Attack|Alert,Anomalous log).
     */
    @Test
    public void CNXTest1() {
        String queryVariable = "A";
        String value = "T";

        BayesianNetwork bn = new Network().CNX;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("MAX");
        String[] evidenceArray1 = {"E", "T"};
        String[] evidenceArray2 = {"L", "T"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);
        evidence.add(evidenceArray2);

        double answer = infer.eliminate(CNX, queryVariable, value, order, evidence);
        assertEquals(answer, 0.84109, 0.0001);
    }

    /**
     * Testing a query on CNX. P(Maintenance|Attack, ~Alert).
     */
    @Test
    public void CNXTest2() {
        String queryVariable = "M";
        String value = "T";

        BayesianNetwork bn = new Network().CNX;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("GREEDY");
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

        BayesianNetwork bn = new Network().BND;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("MAX");
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

        BayesianNetwork bn = new Network().BND;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("GREEDY");
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

        BayesianNetwork bn = new Network().BNA;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("MAX");
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

        BayesianNetwork bn = new Network().BNA;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("GREEDY");
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

        BayesianNetwork bn = new Network().BNA;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("MAX");
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

        BayesianNetwork bn = new Network().BNA;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("GREEDY");
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

        BayesianNetwork bn = new Network().BNA;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("MAX");
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

        BayesianNetwork bn = new Network().BNB;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("GREEDY");
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

        BayesianNetwork bn = new Network().BNB;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("MAX");
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

        BayesianNetwork bn = new Network().BNB;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("GREEDY");
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

        BayesianNetwork bn = new Network().BNB;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("MAX");
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

        BayesianNetwork bn = new Network().BNB;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("GREEDY");
        String[] evidenceArray1 = {"J", "T"};
        String[] evidenceArray2 = {"L", "F"};
        ArrayList<String[]> evidence = new ArrayList<>();
        evidence.add(evidenceArray1);
        evidence.add(evidenceArray2);

        double answer = infer.eliminate(BNB, queryVariable, value, order, evidence);
        assertEquals(answer, 0.45600, 0.0001);
    }

    /**
     * Stacscheck test for BNC.
     */
    @Test
    public void BNCTest1() {
        String queryVariable = "Z";
        String value = "T";

        BayesianNetwork bn = new Network().BNC;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("MAX");
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

        BayesianNetwork bn = new Network().BNC;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("GREEDY");
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

        BayesianNetwork bn = new Network().BNC;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("MAX");
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

        BayesianNetwork bn = new Network().BNC;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("GREEDY");
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

        BayesianNetwork bn = new Network().BNC;
        OrderChoice oc = new OrderChoice(bn, queryVariable);
        String[] order = oc.search("MAX");
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
}
