package tests;

import org.junit.Before;
import org.junit.Test;
import support.BayesianNetwork;
import support.EvidenceInference;
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

    /**
     * Set up EvidenceInference object and networks before each test.
     */
    @Before
    public void setUp() {
        infer = new EvidenceInference();
        BNA = network.BNA;
        BNB = network.BNB;
        BNC = network.BNC;
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
}
