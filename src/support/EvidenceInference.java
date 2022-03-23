package support;

import java.util.ArrayList;
import java.util.HashMap;

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

        int pairs = factors.size() - 1;
        for (int i = 0; i < pairs * 2; i += 2) {
            Factor first = factors.get(i);
            Factor second = factors.get(i + 1);
            Factor joined = join(first, second);
            factors.add(joined);
        }

        Factor finalFactor = factors.get(factors.size() - 1);
        normalise(finalFactor);
        HashMap<String, Double> map = finalFactor.getProbabilities();
        if (value.equals("T")) {
            map.get("1");
        }

        return map.get("0");
    }

    //TODO test
    public void normalise(Factor f1) {
        HashMap<String, Double> cpt = f1.getProbabilities();
        Double norm = getSumOfProbabilities(cpt);

        for (String key : cpt.keySet()) {
            Double newProb = cpt.get(key) / norm;
            cpt.replace(key, newProb);
        }
    }

    //TODO test
    public Double getSumOfProbabilities(HashMap<String, Double> cpt) {
        Double sum = 0.0;

        for (String key : cpt.keySet()) {
            sum += cpt.get(key);
        }

        return sum;
    }
}
