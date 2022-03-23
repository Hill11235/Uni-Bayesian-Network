package support;

import java.util.ArrayList;

public class EvidenceInference extends SimpleInference {

    //TODO override prune method so that it also checks against evidence ancestors.
    public String[] prune(BayesianNetwork bn, String queryVariable, String[] order, String[] evidence) {
        return null;
    }

    //TODO add evidence projection method to this class and superclass.
    //TODO add evidence converter as part of above method?
    public ArrayList<Factor> projectEvidence(ArrayList<Factor> factors, String[] evidence) {
        return factors;
    }

    //TODO override getValue method to include functionality for multiple factors and normalisation.
    public double getValue(ArrayList<Factor> factors, String value) {

        return 0.0;
    }
}
