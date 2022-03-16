package support;

import java.util.ArrayList;

/**
 * Class used to represent the CPT for a given Node.
 */
public class Factor {

    //TODO consider how to map the probabilities to the labels as this could be useful for marginalisation

    private ArrayList<String> nodeLabels;
    private int numRows = 2;
    private ArrayList<Double> probabilities = new ArrayList<>();

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
        for (double prob : probs) {
            probabilities.add(prob);
        }
    }
}
