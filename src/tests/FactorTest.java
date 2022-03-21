package tests;

import org.junit.Before;
import org.junit.Test;
import support.Factor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Testing Factor class.
 */
public class FactorTest {

    private Factor cpt;
    private String label1 = "P";
    private String label2 = "Q";
    private ArrayList<String> labels = new ArrayList<>(Arrays.asList(label1, label2));

    /**
     * Set up fresh Factor object for each test.
     */
    @Before
    public void setUp() {
        cpt = new Factor(labels);
    }

    /**
     * Test correct labels are returned.
     */
    @Test
    public void testGetLabels() {
        assertEquals(labels, cpt.getNodeLabels());
    }

    /**
     * Test that valid probabilities can be added and returned as expected.
     */
    @Test
    public void testAddValidProbabilities() {
        HashMap<String, Double> expected = addValidProb();
        assertEquals(cpt.getProbabilities(), expected);
    }

    /**
     * Test that adding the incorrect number probabilities throws a RuntimeException.
     */
    @Test
    public void testAddingInvalidProbabilities() {
        Double d1 = 0.8;
        try {
            cpt.addProbabilities(d1);
            fail();
        } catch (RuntimeException ignored) {
        }
    }

    /**
     * Tests that the Factor creates the correct number of rows based on the labels.
     */
    @Test
    public void testGetNumRows() {
        assertEquals(cpt.getNumRows(), 4);
    }

    /**
     * Helper method for adding probabilities.
     * @return list of probabilities associated with table.
     */
    private HashMap<String, Double> addValidProb() {
        Double p1 = 0.9;
        Double p2 = 0.1;
        Double p3 = 0.3;
        Double p4 = 0.7;

        HashMap<String, Double> output = new HashMap<>();
        output.put("00", 0.9);
        output.put("01", 0.1);
        output.put("10", 0.3);
        output.put("11", 0.7);

        cpt.addProbabilities(p1, p2, p3, p4);
        return output;
    }
}
