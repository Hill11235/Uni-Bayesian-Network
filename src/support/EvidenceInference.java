package support;

import java.util.ArrayList;

public class EvidenceInference extends SimpleInference {

    //TODO override prune method so that it also checks against evidence ancestors.
    public String[] prune(BayesianNetwork bn, String queryVariable, String[] order, ArrayList<String[]> evidence) {
        //TODO use prune in superclass and add another shell method to override in this class.
        return null;
    }

    //TODO add evidence projection method to this class and superclass.
    //TODO add evidence converter as part of above method?
    public ArrayList<Factor> projectEvidence(ArrayList<Factor> factors, ArrayList<String[]> evidence) {
        return factors;
    }

    //TODO override getValue method to include functionality for multiple factors and normalisation.
    public double getValue(ArrayList<Factor> factors, String value) {

        if (factors.size() == 1) {
            return super.getValue(factors, value);
        }
        //use super.join to iteratively combine Factors.
        //normalise
        //return requested value from here or using super.getValue with reduced list.

        return 0.0;
    }
}
