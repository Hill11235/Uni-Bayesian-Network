package tests;

import org.junit.Before;
import org.junit.Test;
import support.BayesianNetwork;
import support.Network;
import support.SimpleInference;

import static org.junit.Assert.assertEquals;

/**
 * Used to test full inference as per stacscheck and with some additional tests.
 */
public class P2Tests {

    Network network = new Network();
    SimpleInference infer;
    BayesianNetwork BNA;
    BayesianNetwork BNB;
    BayesianNetwork BNC;

    /**
     * Set up SimpleInference object and networks before each test.
     */
    @Before
    public void setUp() {
        infer = new SimpleInference();
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

        double answer = infer.eliminate(BNA, queryVariable, value, order, null);
        assertEquals(answer, 0.57050, 0.000001);
    }

    /**
     * Stacscheck test for BNA.
     */
    @Test
    public void BNATest2() {
        String queryVariable = "D";
        String value = "T";
        String[] order = {"B", "C", "A"};

        double answer = infer.eliminate(BNA, queryVariable, value, order, null);
        assertEquals(answer, 0.57050, 0.000001);
    }

    /**
     * Stacscheck test for BNB.
     */
    @Test
    public void BNBTest1() {
        String queryVariable = "N";
        String value = "T";
        String[] order = {"J", "L", "K", "M", "O"};

        double answer = infer.eliminate(BNB, queryVariable, value, order, null);
        assertEquals(answer, 0.39864, 0.000001);
    }

    /**
     * Stacscheck test for BNB.
     */
    @Test
    public void BNBTest2() {
        String queryVariable = "M";
        String value = "T";
        String[] order = {"J", "L", "K", "N", "O"};

        double answer = infer.eliminate(BNB, queryVariable, value, order, null);
        assertEquals(answer, 0.49660, 0.000001);
    }

    /**
     * Stacscheck test for BNC.
     */
    @Test
    public void BNCTest1() {
        String queryVariable = "U";
        String value = "T";
        String[] order = {"P", "R", "Z", "S", "Q", "V"};

        double answer = infer.eliminate(BNC, queryVariable, value, order, null);
        assertEquals(answer, 0.42755, 0.000001);
    }

    /**
     * Stacscheck test for BNC.
     */
    @Test
    public void BNCTest2() {
        String queryVariable = "S";
        String value = "T";
        String[] order = {"P", "U", "R", "Z", "Q", "V"};

        double answer = infer.eliminate(BNC, queryVariable, value, order, null);
        assertEquals(answer, 0.49660, 0.000001);
    }

    /**
     * Own, false test for BNC.
     */
    @Test
    public void BNCTest3() {
        String queryVariable = "S";
        String value = "F";
        String[] order = {"P", "U", "R", "Z", "Q", "V"};

        double answer = infer.eliminate(BNC, queryVariable, value, order, null);
        assertEquals(answer, 1 - 0.49660, 0.000001);
    }
}
