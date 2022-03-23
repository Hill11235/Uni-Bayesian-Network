package support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * P2 class, used for making simple inferences.
 */
public class SimpleInference {

    /**
     * Given a network, make a simple inference. Want the value of a query Variable, reduce variables in the order provided.
     * @param bn network to be queried.
     * @param queryVariable variable being queried.
     * @param value T/F value of query.
     * @param order reduction order of variables.
     * @return requested probability based on network.
     */
    public double eliminate(BayesianNetwork bn, String queryVariable, String value, String[] order, String[] evidence) {

        String[] prunedOrder = prune(bn, queryVariable, order);
        ArrayList<Factor> factors = createFactors(bn, queryVariable, prunedOrder);
        ArrayList<Factor> projectedFactors = projectEvidence(factors, evidence);

        for (String label : prunedOrder) {
            ArrayList<Factor> toSumOut = getRelatedFactors(projectedFactors, label);
            for (Factor cpt : toSumOut) {
                projectedFactors.remove(cpt);
            }

            Factor newFactor = joinMarginalise(toSumOut, label);
            projectedFactors.add(newFactor);
        }

        return getValue(projectedFactors, value);
    }

    /**
     * Takes the specified order and removes any variables which are not ancestors of the query variable.
     * @param bn network to be queried.
     * @param queryVariable variable being queried.
     * @param order reduction order of variables.
     * @return updated order.
     */
    public String[] prune(BayesianNetwork bn, String queryVariable, String[] order) {
        Node queryNode = bn.getNode(queryVariable);
        ArrayList<Node> ancestors = queryNode.getAllAncestors();
        ArrayList<String> labels = bn.getLabelList(ancestors);
        List<String> orderList = new ArrayList<>(Arrays.asList(order));
        orderList.retainAll(labels);
        String[] updatedOrder = new String[orderList.size()];
        orderList.toArray(updatedOrder);

        return updatedOrder;
    }

    /**
     * Loops through the order and gets the Factor linked to each variable and adds it to a list.
     * Also adds the query variable's factor to the list.
     * @param bn network to be queried.
     * @param queryVariable variable being queried.
     * @param order reduction order of variables.
     * @return list of Factors that will need to be joined and marginalised.
     */
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

    /**
     * Projects provided evidence across applicable Factors.
     * Method to be overwritten in subclass that makes use of evidence.
     * @param factors arraylist of Factors to be used.
     * @param evidence certain variables are set to true or false.
     * @return list of factors with evidence applied.
     */
    public ArrayList<Factor> projectEvidence(ArrayList<Factor> factors, String[] evidence) {
        return factors;
    }

    /**
     * Given a list of Factors and a label. Return all the Factors from the list which contain that label.
     * @param factors list of Factors.
     * @param label label that Factors must have.
     * @return list of Factors linked by that label.
     */
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

    /**
     * Given a list of Factors with a common label, join and marginalise those Factors until there is only one Factor without that label.
     * @param toSumOut list of Factors to be reduced.
     * @param label variable that is to be removed from the Factors.
     * @return one joined and marginalised Factor.
     */
    public Factor joinMarginalise(ArrayList<Factor> toSumOut, String label) {

        int pairs = toSumOut.size() - 1;

        for (int i = 0; i < pairs * 2; i += 2) {
            Factor first = toSumOut.get(i);
            Factor second = toSumOut.get(i + 1);
            Factor joined = join(first, second);
            toSumOut.add(joined);
        }

        int finalSize = toSumOut.size();
        return marginalise(toSumOut.get(finalSize - 1), label);
    }

    /**
     * Takes two Factor with one or more common labels and combines them into one larger joint probability table.
     * @param first first Factor to be joined.
     * @param second second Factor to be joined.
     * @return joined Factor of joint probability distribution.
     */
    public Factor join(Factor first, Factor second) {
        ArrayList<String> allLabels = getAllLabels(first, second);
        Factor f3 = new Factor(allLabels);
        generateCombinedProbabilities(f3, first, second);

        return f3;
    }

    /**
     * Updates the probabilities in the new joined Factor.
     * Steps:
     * 1. Generate label lists for each of the sub tables.
     * 2. For each key in the new Factor, create maps with each label and T/F value for each sub-CPT.
     * 3. Using the maps, get the necessary probability from each underlying table.
     * 4. Take the product of these probabilities and put into the joined Factor's CPT.
     * @param f3 join Factor.
     * @param f1 first Factor to be joined.
     * @param f2 second Factor to be joined.
     */
    public void generateCombinedProbabilities(Factor f3, Factor f1, Factor f2) {
        ArrayList<String> v1v2 = getV1V2(f1, f2);
        ArrayList<String> v1v3 = getV1V3(f1, f2);
        ArrayList<String> f3Labels = f3.getNodeLabels();
        HashMap<String, Double> cptF3 = f3.getProbabilities();

        for (String key : cptF3.keySet()) {
            HashMap<String, String> v1v2LabelMapping = getLabelMapping(v1v2, f3Labels, key);
            HashMap<String, String> v1v3LabelMapping = getLabelMapping(v1v3, f3Labels, key);

            Double f1Term = getProbFromFactor(v1v2LabelMapping, f1);
            Double f2Term = getProbFromFactor(v1v3LabelMapping, f2);

            cptF3.replace(key, f1Term * f2Term);
        }
    }

    /**
     * Create a mapping of labels to T/F value for each key in the joined CPT.
     * @param combinedLabels either v1v2 or v1v3.
     * @param f3Labels labels in new CPT.
     * @param key true/false concat for each row of joined Factor.
     * @return mapping of each label to T/F.
     */
    private HashMap<String, String> getLabelMapping(ArrayList<String> combinedLabels, ArrayList<String> f3Labels, String key) {
        HashMap<String, String> labelMapping = new HashMap<>();
        for (String vLabel : combinedLabels) {
            int position = f3Labels.indexOf(vLabel);
            char tf = key.charAt(position);
            labelMapping.put(vLabel, Character.toString(tf));
        }
        return labelMapping;
    }

    /**
     * Given a map of labels to T/F values, loop through Factor's labels and construct necessary key.
     * Use constructed key to get probability from underlying Factor map.
     * @param labelMapping variable names mapped to needed T/F value.
     * @param queryFactor Factor that the probability is to be taken from.
     * @return probability of given label's combination of T/F values.
     */
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

    /**
     * Return the list of labels which are common to both provided Factors plus labels in f1 only.
     * Essentially generate labels in f1.
     * @param f1 first Factor.
     * @param f2 second Factor.
     * @return list of labels.
     */
    public ArrayList<String> getV1V2(Factor f1, Factor f2) {
        ArrayList<String> v1v2 = findCommonLabels(f1, f2);
        ArrayList<String> v2 = findFirstOnlyLabels(f1, f2);
        v1v2.addAll(v2);

        return v1v2;
    }

    /**
     * Return the list of labels which are common to both provided Factors plus labels in f2 only.
     * Essentially generate labels in f2.
     * @param f1 first Factor.
     * @param f2 second Factor.
     * @return list of labels.
     */
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

    /**
     * Take a Factor and a label to remove. Create a Factor without that label and the with other probabilities summed.
     * @param toReduce CPT to be marginalised.
     * @param label label to be removed from original CPT.
     * @return new marginalised Factor with label removed.
     */
    public Factor marginalise(Factor toReduce, String label) {
        ArrayList<String> newLabels = toReduce.getNodeLabels();
        newLabels.remove(label);

        Factor reducedFactor = new Factor(newLabels);
        HashMap<String, Double> cpt = reducedFactor.getProbabilities();
        HashMap<String, Double> largeCPT = toReduce.getProbabilities();

        for (String key : cpt.keySet()) {
            Double prob = 0.0;
            HashMap<String, String> labelMapping = getLabelMapping(newLabels, newLabels, key);
            HashMap<Integer, String> positionMapping = getPositionMapping(labelMapping, toReduce);

            for (String reduceKey : largeCPT.keySet()) {
                if (checkMatch(reduceKey, positionMapping)) {
                    prob += largeCPT.get(reduceKey);
                }
            }
            cpt.replace(key, prob);
        }

        return reducedFactor;
    }

    /**
     * For a given probability key, check whether this matches all the individual label T/F we want.
     * @param reduceKey key to check against. Of form of repeating 1s and 0s, e.g. "01110".
     * @param positionMapping maps position of each label in the String to the desired T/F outcome.
     * @return true if all labels in the key match what is required, false otherwise.
     */
    public boolean checkMatch(String reduceKey, HashMap<Integer, String> positionMapping) {

        for (Integer position : positionMapping.keySet()) {
            char value = reduceKey.charAt(position);
            String tf = Character.toString(value);
            String desiredTF = positionMapping.get(position);

            if (!tf.equals(desiredTF)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Updates label to value mapping to mapping (position of each label in Factor to be reduced) to value.
     * @param labelMapping mapping of labels to T/F value in String form.
     * @param toReduce Factor to be reduced and what the positions of each label are derived from.
     * @return map of label position to T/F value.
     */
    public HashMap<Integer, String> getPositionMapping(HashMap<String, String> labelMapping, Factor toReduce) {
        HashMap<Integer, String> positionMapping = new HashMap<>();
        ArrayList<String> reduceLabels = toReduce.getNodeLabels();

        for (String label : labelMapping.keySet()) {
            int position = reduceLabels.indexOf(label);
            positionMapping.put(position, labelMapping.get(label));
        }

        return positionMapping;
    }

    /**
     * Given a factor of only two elements, return the probability of T/F as requested.
     * @param factors list of Factors, should only contain one Factor at this point.
     * @param value true or false value in form of "1" or "0".
     * @return probability in Double form.
     */
    public double getValue(ArrayList<Factor> factors, String value) {
        Factor cpt = factors.get(0);
        HashMap<String, Double> probabilities = cpt.getProbabilities();

        if (value.equals("F")) {
            return probabilities.get("0");
        }
        return probabilities.get("1");
    }
}
