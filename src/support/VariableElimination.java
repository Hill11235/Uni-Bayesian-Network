package support;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        ArrayList<String> allLabels = getAllLabels(first, second);
        Factor f3 = new Factor(allLabels);
        generateCombinedProbabilities(f3, first, second);

        return f3;
    }

    //TODO implement and test
    public void generateCombinedProbabilities(Factor f3, Factor f1, Factor f2) {

        ArrayList<String> v1v2 = getV1V2(f1, f2);
        ArrayList<String> v1v3 = getV1V3(f1, f2);
        ArrayList<String> f3Labels = f3.getNodeLabels();
        HashMap<String, Double> cptF3 = f3.getProbabilities();

        for (String key : cptF3.keySet()) {
            HashMap<String, String> v1v2LabelMapping = new HashMap<>();
            HashMap<String, String> v1v3LabelMapping = new HashMap<>();

            for (String vLabel : v1v2) {
                int position = f3Labels.indexOf(vLabel);
                char tf = key.charAt(position);
                v1v2LabelMapping.put(vLabel, Character.toString(tf));
            }
            for (String vLabel : v1v3) {
                int position = f3Labels.indexOf(vLabel);
                char tf = key.charAt(position);
                v1v3LabelMapping.put(vLabel, Character.toString(tf));
            }

            Double f1Term = getProbFromFactor(v1v2LabelMapping, f1);
            Double f2Term = getProbFromFactor(v1v3LabelMapping, f2);

            cptF3.replace(key, f1Term * f2Term);
        }
    }

    //TODO implement and test
    public Double getProbFromFactor(HashMap<String, String> labelMapping, Factor queryFactor) {
        ArrayList<String> labels = queryFactor.getNodeLabels();
        HashMap<String, Double> cpt = queryFactor.getProbabilities();
        StringBuilder builtKey = new StringBuilder();

        for (String label : labels) {
            String tf = labelMapping.get(label);
            builtKey.append(tf);
        }
        String key = builtKey.toString();

        return cpt.get(key);
    }

    //TODO implement and test
    public ArrayList<String> getV1V2(Factor f1, Factor f2) {
        ArrayList<String> v1v2 = findCommonLabels(f1, f2);
        ArrayList<String> v2 = findFirstOnlyLabels(f1, f2);
        v1v2.addAll(v2);

        return v1v2;
    }

    //TODO implement and test
    public ArrayList<String> getV1V3(Factor f1, Factor f2) {
        ArrayList<String> v1v3 = findCommonLabels(f1, f2);
        ArrayList<String> v3 = findSecondOnlyLabels(f1, f2);
        v1v3.addAll(v3);

        return v1v3;
    }

    public ArrayList<String> getAllLabels(Factor first, Factor second) {
        ArrayList<String> commonLabels = findCommonLabels(first, second);
        ArrayList<String> firstOnlyLabels = findFirstOnlyLabels(first, second);
        ArrayList<String> secondOnlyLabels = findSecondOnlyLabels(first, second);

        return Stream.of(commonLabels, firstOnlyLabels, secondOnlyLabels)
                .flatMap(Collection::stream).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> findCommonLabels(Factor first, Factor second) {
        ArrayList<String> firstLabels = first.getNodeLabels();
        ArrayList<String> secondLabels = second.getNodeLabels();
        firstLabels.retainAll(secondLabels);

        return firstLabels;
    }

    public ArrayList<String> findFirstOnlyLabels(Factor first, Factor second) {
        ArrayList<String> firstLabels = first.getNodeLabels();
        ArrayList<String> secondLabels = second.getNodeLabels();
        firstLabels.removeAll(secondLabels);

        return firstLabels;
    }

    public ArrayList<String> findSecondOnlyLabels(Factor first, Factor second) {
        ArrayList<String> firstLabels = first.getNodeLabels();
        ArrayList<String> secondLabels = second.getNodeLabels();
        secondLabels.removeAll(firstLabels);

        return secondLabels;
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
