package support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EvidenceInference extends SimpleInference {

    /**
     * Takes order provided by user and removes variables which are not ancestors of the query or evidence variables.
     * If an evidence variable's ancestors do not overlap with the query variable then these are not included.
     * @param bn network to be queried.
     * @param queryVariable variable being queried.
     * @param order reduction order of variables.
     * @param evidence evidence provided by user for query.
     * @return updated order.
     */
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
                labels.add(evidenceNode.getLabel());
            }
        }
        labels.remove(queryVariable);

        List<String> orderList = new ArrayList<>(Arrays.asList(order));
        orderList.retainAll(labels);

        String[] updatedOrder = new String[orderList.size()];
        orderList.toArray(updatedOrder);

        return updatedOrder;
    }

    /**
     * Takes two ArrayLists of Strings and checks whether the intersection of the two lists is empty.
     * @param list1 first list to be checked.
     * @param list2 second list to be checked.
     * @return true if lists have no elements in common.
     */
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

    public boolean factorContainsVariable(Factor factor, String[] evidencePair) {
        String label = evidencePair[0];
        ArrayList<String> factorLabels = factor.getNodeLabels();

        return factorLabels.contains(label);
    }

    public void setProbability(Factor factor, String[] evidencePair) {
        String label = evidencePair[0];
        String tf = probConverter(evidencePair[1]);

        ArrayList<String> factorLabels = factor.getNodeLabels();
        int position = factorLabels.indexOf(label);
        HashMap<String, Double> cpt = factor.getProbabilities();

        for (String key : cpt.keySet()) {
            char relevantBoolean = key.charAt(position);
            if (!tf.equals(Character.toString(relevantBoolean))) {
                cpt.replace(key, 0.0);
            }
        }
    }

    private String probConverter(String tf) {
        if (tf.equals("T")) {
            return "1";
        }
        return "0";
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
