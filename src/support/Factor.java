package support;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class used to represent the CPT for a given Node.
 */
public class Factor {

    //TODO consider how to map the probabilities to the labels as this could be useful for marginalisation
    //TODO implement probabilities as a hashmap with String label permutation as key and prob as value?

    private ArrayList<String> nodeLabels;
    private int numRows = 2;
    private HashMap<String, Double> probabilities = new HashMap<>();

    public Factor(ArrayList<String> nodeLabels) {
        this.nodeLabels = nodeLabels;
        if (nodeLabels != null) {
            this.numRows = (int) Math.pow(2, nodeLabels.size());
        }
    }

    /**
     * Add the varargs probs to the list of probabilities in this CPT.
     * @param probs probabilities to be added.
     */
    public void addProbabilities(double ... probs) {
        if (probs.length != numRows) {
            throw new RuntimeException("Number of probabilities needs to match CPT length");
        }

        int i = 0;
        for (double prob : probs) {
            int repeat = (nodeLabels.size()) - Integer.toBinaryString(i).length();
            String truths = "0".repeat(repeat) + Integer.toBinaryString(i);
            probabilities.put(truths, prob);
            i++;
        }
    }

    /**
     * Gst probabilities associated with Factor.
     * @return list of probabilities.
     */
    public HashMap<String, Double> getProbabilities() {
        return probabilities;
    }

    /**
     * Get nunber of rows in the table.
     * @return row number integer.
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Get Node labels associated with Factor.
     * @return list of labels.
     */
    public ArrayList<String> getNodeLabels() {
        return nodeLabels;
    }
}
