package support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VariableElimination {

    private Network networks = new Network();

    //Node as String letter
    //value as T/F
    //Order as String array
    private ArrayList<Factor> factors = new ArrayList<>();

    //TODO test
    public double eliminate(BayesianNetwork bn, String queryVariable, String value, String[] order) {

        String[] prunedOrder = prune(bn, queryVariable, order);
        ArrayList<Factor> factors = createFactors(bn, queryVariable, order);

        for (String label : prunedOrder) {
            ArrayList<Factor> toSumOut = getRelatedFactors(factors, label);
            for (Factor cpt : toSumOut) {
                factors.remove(cpt);
            }

            Factor newFactor = joinMarginalise(toSumOut, label);
            factors.add(newFactor);
        }

        return getValue(factors, value);
    }

    //tested
    public String[] prune(BayesianNetwork bn, String queryVariable, String[] order) {
        ArrayList<Node> nodes = bn.getNodes();
        Node queryNode = bn.getNode(queryVariable);
        ArrayList<Node> ancestors = queryNode.getAllAncestors();
        ArrayList<String> labels = bn.getLabelList(ancestors);
        List<String> orderList = new ArrayList<>(Arrays.asList(order));
        orderList.retainAll(labels);
        String[] updatedOrder = new String[orderList.size()];
        orderList.toArray(updatedOrder);

        return updatedOrder;
    }

    //tested
    public ArrayList<Factor> createFactors(BayesianNetwork bn, String queryVariable, String[] order) {
        ArrayList<Factor> factors = new ArrayList<>();
        Node queryNode = bn.getNode(queryVariable);
        factors.add(queryNode.getCpt());

        for (String label : order) {
            Node nd = bn.getNode(label);
            factors.add(nd.getCpt());
        }

        return factors;
    }

    //TODO test
    public ArrayList<Factor> getRelatedFactors(ArrayList<Factor> factors, String label) {
        ArrayList<Factor> relatedFactors = new ArrayList<>();

        for (Factor cpt : factors) {
            ArrayList<String> labels = cpt.getNodeLabels();
            if (labels.contains(label)) {
                relatedFactors.add(cpt);
            }
        }

        return relatedFactors;
    }

    //TODO implement, create a new factor with all variables in Factors of toSumOut but without label.
    public Factor joinMarginalise(ArrayList<Factor> toSumOut, String label) {

        return null;
    }

    //tested
    public double getValue(ArrayList<Factor> factors, String value) {
        Factor cpt = factors.get(0);
        ArrayList<Double> probabilities = cpt.getProbabilities();

        if (value.equals("F")) {
            return probabilities.get(0);
        }
        return probabilities.get(1);
    }
}
