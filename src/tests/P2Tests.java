package tests;

import org.junit.Before;
import org.junit.Test;
import support.BayesianNetwork;
import support.Network;
import logic.SimpleInference;

import static org.junit.Assert.assertEquals;

/**
 * Used to test full inference as per stacscheck and with some additional tests.
 */
public class P2Tests {

    Network network = new Network();
    SimpleInference infer;
    BayesianNetwork CNX;
    BayesianNetwork BNA;
    BayesianNetwork BNB;
    BayesianNetwork BNC;
    BayesianNetwork BND;

    /**
     * Set up SimpleInference object and networks before each test.
     */
    @Before
    public void setUp() {
        infer = new SimpleInference();
        BNA = network.BNA;
        BNB = network.BNB;
        BNC = network.BNC;
        BND = network.BND;
        CNX = network.CNX;
    }

    /**
     * Test query on CNX. P(Attack).
     */
    @Test
    public void CNXTest1() {
        String queryVariable = "A";
        String value = "T";
        String[] order = {"I", "B", "H", "M", "W", "A", "E", "L"};

        double answer = infer.eliminate(CNX, queryVariable, value, order, null);
        assertEquals(answer, 0.0233, 0.0001);
    }

    /**
     * Test query on CNX. P(Alert).
     */
    @Test
    public void CNXTest2() {
        String queryVariable = "E";
        String value = "T";
        String[] order = {"I", "B", "H", "M", "W", "A", "E", "L"};

        double answer = infer.eliminate(CNX, queryVariable, value, order, null);
        assertEquals(answer, 0.03192, 0.0001);
    }

    /**
     * Test query on CNX. P(Holiday).
     */
    @Test
    public void CNXTest3() {
        String queryVariable = "H";
        String value = "T";
        String[] order = {"I", "B", "H", "M", "W", "A", "E", "L"};

        double answer = infer.eliminate(CNX, queryVariable, value, order, null);
        assertEquals(answer, 0.125, 0.0001);
    }

    /**
     * Test a simple query on the introduced network BND. P(Late).
     */
    @Test
    public void BNDTest1() {
        String queryVariable = "L";
        String value = "T";
        String[] order = {"W", "S", "E", "D", "R", "B", "L"};

        double answer = infer.eliminate(BND, queryVariable, value, order, null);
        assertEquals(answer, 0.1908, 0.0001);
    }

    /**
     * Test a simple query on the introduced network BND. P(Engineering works).
     */
    @Test
    public void BNDTest2() {
        String queryVariable = "E";
        String value = "T";
        String[] order = {"W", "S", "E", "D", "R", "B", "L"};

        double answer = infer.eliminate(BND, queryVariable, value, order, null);
        assertEquals(answer, 0.01585, 0.00001);
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

    /**
     * Test getting probability from parentless Node.
     */
    @Test
    public void BNCTest4() {
        String queryVariable = "P";
        String value = "T";
        String[] order = {"P", "U", "R", "Z", "Q", "V"};

        double answer = infer.eliminate(BNC, queryVariable, value, order, null);
        assertEquals(answer, 0.05, 0.000001);
    }
}
