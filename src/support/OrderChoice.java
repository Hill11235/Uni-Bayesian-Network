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
    public void createUndirectedGraph() {
        ArrayList<Node> nodes = bn.getNodes();

        addParentForEachChild(nodes);
        addLinksBetweenParents(nodes);
    }

    /**
     * Loops through a list of Nodes and links the parents of each Node which are not already linked.
     * @param nodes list of Nodes to loop through.
     */
    public void addLinksBetweenParents(ArrayList<Node> nodes) {
        for (Node node : nodes) {
            ArrayList<Node> parents = node.getParents();

            if (parents.size() > 1) {
                ArrayList<Integer> elementList = getIntegerList(parents.size());
                List<Set<Integer>> combos = getKCombinations(elementList, 2);

                for (Set<Integer> parentCombo : combos) {

                    if (!areParentsConnected(parents, parentCombo)) {
                        connectParents(parents, parentCombo);
                    }
                }
            }
        }
    }

    /**
     * Checks whether two Nodes are parents of each other.
     * @param parents the list of parents for a given Node.
     * @param parentCombo the two parent combination to be extracted.
     * @return true if Nodes are interlinked.
     */
    public boolean areParentsConnected(ArrayList<Node> parents, Set<Integer> parentCombo) {
        ArrayList<Node> combo = getParentComboFromSet(parents, parentCombo);
        Node parent1 = combo.get(0);
        Node parent2 = combo.get(1);

        ArrayList<Node> parent1Parents = parent1.getParents();
        ArrayList<Node> parent2Parents = parent2.getParents();

        return (parent1Parents.contains(parent2)) && (parent2Parents.contains(parent1));
    }

    /**
     * Takes a combination of Nodes and makes them parents of one another.
     * @param parents the list of parents for a given Node.
     * @param parentCombo the two parent combination to be extracted.
     */
    public void connectParents(ArrayList<Node> parents, Set<Integer> parentCombo) {
        ArrayList<Node> combo = getParentComboFromSet(parents, parentCombo);
        Node parent1 = combo.get(0);
        Node parent2 = combo.get(1);

        parent1.addParent(parent2);
        parent2.addParent(parent1);
    }

    /**
     * Given the list the of Nodes and a combination in a set. Get the combination from the list and store in a new list.
     * @param parents the list of parents for a given Node.
     * @param parentCombo the two parent combination to be extracted.
     * @return the pair of parent Nodes in a list.
     */
    public ArrayList<Node> getParentComboFromSet(ArrayList<Node> parents, Set<Integer> parentCombo) {
        ArrayList<Node> combo = new ArrayList<>();

        for (Integer position : parentCombo) {
            combo.add(parents.get(position));
        }

        return combo;
    }

    /**
     * Given a list of Nodes, for each Node it adds each child Node as a parent to its parent Node.
     * So for A --> B, we would then have A <--> B.
     * @param nodes list of Nodes to loop through.
     */
    public void addParentForEachChild(ArrayList<Node> nodes) {
        for (Node node : nodes) {
            ArrayList<Node> parents = node.getParents();
            for (Node parent : parents) {
                parent.addParent(node);
            }
        }
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
