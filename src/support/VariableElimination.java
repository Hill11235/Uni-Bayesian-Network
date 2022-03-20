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

        String[] prunedOrder = prune(queryVariable, order);
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
    public String[] prune(String queryVariable, String[] order) {

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

    //TODO implement, finalised factors to contain only one Factor, get requested value from this table.
    public double getValue(ArrayList<Factor> factors, String value) {

        return 0;
    }
}
