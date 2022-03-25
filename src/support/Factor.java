package support;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class used to represent the CPT for a given Node.
 */
public class Factor {

    private final ArrayList<String> nodeLabels;
    private int numRows = 2;
    private final HashMap<String, Double> probabilities = new HashMap<>();

    /**
     * Constructor for class.
     * @param nodeLabels list of Node labels to be included in this Factor.
     */
    public Factor(ArrayList<String> nodeLabels) {
        this.nodeLabels = nodeLabels;
        if (nodeLabels != null) {
            this.numRows = (int) Math.pow(2, nodeLabels.size());
        }
        addRowsToMap();
    }

    /**
     * Adds the necessary rows to the Factor CPT, which is contained in the probabilities HashMap.
     * One row for each combination of the labels.
     */
    private void addRowsToMap() {
        for (int i = 0; i < numRows; i++) {
            int repeat = (nodeLabels.size()) - Integer.toBinaryString(i).length();
            String truths = "0".repeat(repeat) + Integer.toBinaryString(i);
            probabilities.put(truths, 0.0);
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

        ArrayList<Double> probList = new ArrayList<>();
        for (double prob : probs) {
            probList.add(prob);
        }

        for (int i = 0; i < numRows; i++) {
            int repeat = (nodeLabels.size()) - Integer.toBinaryString(i).length();
            String truths = "0".repeat(repeat) + Integer.toBinaryString(i);
            Double prob = probList.get(i);
            probabilities.replace(truths, prob);
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
        return new ArrayList<>(nodeLabels);
    }
}
