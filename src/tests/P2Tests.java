package tests;

import org.junit.Before;
import org.junit.Test;
import support.BayesianNetwork;
import support.Network;
import support.VariableElimination;

import static org.junit.Assert.assertEquals;

/**
 * Used to test full inference as per stacscheck.
 */
public class P2Tests {

    Network network = new Network();
    VariableElimination varElim;
    BayesianNetwork BNA;
    BayesianNetwork BNB;
    BayesianNetwork BNC;

    @Before
    public void setUp() {
        varElim = new VariableElimination();
        BNA = network.BNA;
        BNB = network.BNB;
        BNC = network.BNC;
    }

    @Test
    public void BNATest1() {
        String queryVariable = "D";
        String value = "T";
        String[] order = {"A", "B", "C"};

        double answer = varElim.eliminate(BNA, queryVariable, value, order);
        assertEquals(answer, 0.57050, 0.000001);
    }

    @Test
    public void BNATest2() {
        String queryVariable = "D";
        String value = "T";
        String[] order = {"B", "C", "A"};

        double answer = varElim.eliminate(BNA, queryVariable, value, order);
        assertEquals(answer, 0.57050, 0.000001);
    }

    @Test
    public void BNBTest1() {
        String queryVariable = "N";
        String value = "T";
        String[] order = {"J", "L", "K", "M", "O"};

        double answer = varElim.eliminate(BNB, queryVariable, value, order);
        assertEquals(answer, 0.39864, 0.000001);
    }

    @Test
    public void BNBTest2() {
        String queryVariable = "M";
        String value = "T";
        String[] order = {"J", "L", "K", "N", "O"};

        double answer = varElim.eliminate(BNB, queryVariable, value, order);
        assertEquals(answer, 0.49660, 0.000001);
    }

    @Test
    public void BNCTest1() {
        String queryVariable = "U";
        String value = "T";
        String[] order = {"P", "R", "Z", "S", "Q", "V"};

        double answer = varElim.eliminate(BNC, queryVariable, value, order);
        assertEquals(answer, 0.42755, 0.000001);
    }

    @Test
    public void BNCTest2() {
        String queryVariable = "S";
        String value = "T";
        String[] order = {"P", "U", "R", "Z", "Q", "V"};

        double answer = varElim.eliminate(BNC, queryVariable, value, order);
        assertEquals(answer, 0.49660, 0.000001);
    }

    @Test
    public void BNCTest3() {
        String queryVariable = "S";
        String value = "F";
        String[] order = {"P", "U", "R", "Z", "Q", "V"};

        double answer = varElim.eliminate(BNC, queryVariable, value, order);
        assertEquals(answer, 1 - 0.49660, 0.000001);
    }
}
