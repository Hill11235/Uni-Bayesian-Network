package support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class VariableElimination {

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

        //combine tables until there is only one remaining that can be marginalised.
        while (toSumOut.size() > 1) {
            Factor first = toSumOut.remove(0);
            Factor second = toSumOut.remove(1);
            Factor joined = join(first, second, label);
            toSumOut.add(joined);
        }

        return marginalise(toSumOut.get(0), label);
    }

    //TODO implement and test
    public Factor join(Factor first, Factor second, String label) {
        //join step with table with all labels which is multiplied from previous tables to generate new table.

        return null;
    }

    //TODO implement and test
    public Factor marginalise(Factor toReduce, String label) {
        //marginalisation step where label is summed out of the remaining joined Factor.

        return null;
    }

    public double getValue(ArrayList<Factor> factors, String value) {
        Factor cpt = factors.get(0);
        HashMap<String, Double> probabilities = cpt.getProbabilities();

        if (value.equals("F")) {
            return probabilities.get("0");
        }
        return probabilities.get("1");
    }
}
