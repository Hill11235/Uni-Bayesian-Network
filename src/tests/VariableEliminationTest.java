package tests;

import org.junit.Before;
import org.junit.Test;
import support.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
