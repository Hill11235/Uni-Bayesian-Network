package support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderChoice {

    private BayesianNetwork bn;
    private String queryVariable;

    public OrderChoice(BayesianNetwork bn, String queryVariable) {
        this.bn = bn;
        this.queryVariable = queryVariable;
    }

    //TODO implement and test
    public String[] maxCardinalitySearch() {
        //follow algo as per notes
        return null;
    }

    //TODO implement and test
    public String[] greedySearch() {
        //follow algo as per notes
        return null;
    }

    //TODO implement and test
    public void createUndirectedGraph(BayesianNetwork bn) {
        //get list of Nodes in BN
        //loop through and add reciprocating parent for each child.
        //loop through and for each Node with more than one parent, get each possible combo of parents
        //check each combo has a link, if no link, add one (parent and child).
    }

    /**
     * TAKEN FROM MY PREVIOUS CS5011 ASSIGNMENT.
     * Creates list populated with numbers 0 up to numAdjacentCovered - 1.
     * @param numParents size of list to be generated.
     * @return list of integers.
     */
    public ArrayList<Integer> getIntegerList(int numParents) {
        ArrayList<Integer> initialSet = new ArrayList<>();

        for (int i = 0; i < numParents; i++) {
            initialSet.add(i);
        }
        return initialSet;
    }

    /**
     * TAKEN FROM MY PREVIOUS CS5011 ASSIGNMENT WHERE I WROTE THIS K-COMBINATIONS METHOD.
     * Overloaded method which initiates the recursive method to get all the subsets of size k (subsetSize).
     * @param initialSet set from which subsets are to be chosen.
     * @param subsetSize size of the subsets to be created.
     * @return a list of all subsets of requested size.
     */
    public List<Set<Integer>> getKCombinations(ArrayList<Integer> initialSet, int subsetSize) {
        List<Set<Integer>> combinations = new ArrayList<>();
        getKCombinations(initialSet, subsetSize, 0, new HashSet<Integer>(), combinations);
        return combinations;
    }

    /**
     * TAKEN FROM MY PREVIOUS CS5011 ASSIGNMENT WHERE I WROTE THIS K-COMBINATIONS METHOD.
     * Find all subsets of size k in a set of size n. Will create n choose k subsets.
     * @param initialSet set from which subsets are to be chosen.
     * @param subsetSize size of the subsets to be created.
     * @param index index of element from initialSet to be added.
     * @param currentSet set being built throughout the recursion.
     * @param solution the list of subsets that is being added to throughout.
     */
    private void getKCombinations(ArrayList<Integer> initialSet, int subsetSize, int index, Set<Integer> currentSet, List<Set<Integer>> solution) {

        if (currentSet.size() == subsetSize) {
            solution.add(new HashSet<>(currentSet));
            return;
        }

        if (index == initialSet.size()) {
            return;
        }

        Integer randomIndex = initialSet.get(index);
        currentSet.add(randomIndex);

        //recursion with randomIndex included in the subset
        getKCombinations(initialSet, subsetSize, index + 1, currentSet, solution);

        //recursion with randomIndex not included in the subset
        currentSet.remove(randomIndex);
        getKCombinations(initialSet, subsetSize, index + 1, currentSet, solution);
    }
}
