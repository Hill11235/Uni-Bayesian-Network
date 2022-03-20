package tests;

import org.junit.Before;
import org.junit.Test;
import support.Factor;
import support.Node;
import support.VariableElimination;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VariableEliminationTest {

    VariableElimination varElim = new VariableElimination();

    @Test
    public void eliminate() {
    }

    @Test
    public void prune() {
    }

    @Test
    public void createFactors() {
    }

    @Test
    public void getRelatedFactors() {
    }

    @Test
    public void joinMarginalise() {
    }

    @Test
    public void getValue() {
        Factor cpt = new Factor(new ArrayList<>(List.of("A")));
        ArrayList<Factor> factors = new ArrayList<>(List.of(cpt));
        cpt.addProbabilities(0.05, 0.95);
        assertEquals(varElim.getValue(factors, "T"), 0.95, 0.0001);
        assertEquals(varElim.getValue(factors, "F"), 0.05, 0.0001);
    }
}
