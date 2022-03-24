package support;

import java.util.*;
import java.util.stream.Collectors;

public class EvidenceInference extends SimpleInference {

    //TODO override prune method so that it also checks against evidence ancestors.
    @Override
    public String[] prune(BayesianNetwork bn, String queryVariable, String[] order, ArrayList<String[]> evidence) {
        Node queryNode = bn.getNode(queryVariable);
        ArrayList<Node> ancestors = queryNode.getAllAncestors();
        ArrayList<String> labels = bn.getLabelList(ancestors);

        for (String[] pairing : evidence) {
            Node evidenceNode = bn.getNode(pairing[0]);
            ArrayList<Node> evidenceAncestors = evidenceNode.getAllAncestors();
            ArrayList<String> evidenceLabels = bn.getLabelList(evidenceAncestors);

            if (!isIntersectionEmpty(labels, evidenceLabels)) {
                labels.addAll(evidenceLabels);
            }
        }
        labels.remove(queryVariable);

        List<String> orderList = new ArrayList<>(Arrays.asList(order));
        orderList.retainAll(labels);

        String[] updatedOrder = new String[orderList.size()];
        orderList.toArray(updatedOrder);

        return updatedOrder;
    }

    public boolean isIntersectionEmpty(ArrayList<String> list1, ArrayList<String> list2) {
        Set<String> result = list1.stream()
                .distinct()
                .filter(list2::contains)
                .collect(Collectors.toSet());

        return result.isEmpty();
    }

    //TODO add evidence projection method to this class and superclass.
    //TODO add evidence converter as part of above method?
    @Override
    public ArrayList<Factor> projectEvidence(ArrayList<Factor> factors, ArrayList<String[]> evidence) {
        return factors;
    }

    /**
     * Gets requested probability.
     * Overrides method in superclass as in this case there could be multiple Factors in the list.
     * @param factors list of Factors, can contain multiple Factors.
     * @param value true or false value in form of "1" or "0".
     * @return requested probability.
     */
    @Override
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
            return map.get("1");
        }

        return map.get("0");
    }

    /**
     * Normalises a Factor's CPT.
     * @param f1 Factor to be normalised.
     */
    public void normalise(Factor f1) {
        HashMap<String, Double> cpt = f1.getProbabilities();
        Double norm = getSumOfProbabilities(cpt);

        for (String key : cpt.keySet()) {
            Double newProb = cpt.get(key) / norm;
            cpt.replace(key, newProb);
        }
    }

    /**
     * Takes a CPT and finds the total sum of the probabilities within.
     * @param cpt map of T/F values to probabilities.
     * @return sum of probabilities.
     */
    public Double getSumOfProbabilities(HashMap<String, Double> cpt) {
        Double sum = 0.0;

        for (String key : cpt.keySet()) {
            sum += cpt.get(key);
        }

        return sum;
    }
}
