package support;

import java.util.ArrayList;

public class VariableElimination {

    private Network networks = new Network();

    //Node as String letter
    //value as T/F
    //Order as String array
    private ArrayList<Factor> factors = new ArrayList<>();

    //P2 algo
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

    //TODO implement, prune every variable that is not an ancestor of the query Node.
    public String[] prune(BayesianNetwork bn, String queryVariable, String[] order) {
        ArrayList<Node> nodes = bn.getNodes();
        Node queryNode = bn.getNode(queryVariable);
        ArrayList<Node> ancestors = queryNode.getAllAncestors();
        //if label in order is not an ancestor label then remove
        //return updated String array

        return null;
    }

    //TODO implement, copy the factor of each Node in the order and add it to the list.
    public ArrayList<Factor> createFactors(BayesianNetwork bn, String queryVariable, String[] order) {

        return null;
    }

    //TODO implement, loop through provided factor list and add any factors that contain label to list.
    public ArrayList<Factor> getRelatedFactors(ArrayList<Factor> factors, String label) {

        return null;
    }

    //TODO implement, create a new factor with all variables in Factors of toSumOut but without label.
    public Factor joinMarginalise(ArrayList<Factor> toSumOut, String label) {
        return null;
    }

    public double getValue(ArrayList<Factor> factors, String value) {
        Factor cpt = factors.get(0);
        ArrayList<Double> probabilities = cpt.getProbabilities();

        if (value.equals("F")) {
            return probabilities.get(0);
        }
        return probabilities.get(1);
    }
}
