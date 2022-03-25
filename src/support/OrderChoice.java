package support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Searches a provided network to find estimations of the optimal joining order.
 */
public class OrderChoice {

    private final BayesianNetwork bn;
    private final String queryVariable;

    /**
     * Constructor for class. Sets the Bayesian network and query variable for searches.
     * Also creates an undirected graph using the provided network.
     * @param bn BayesianNetwork to use.
     * @param queryVariable variable being queried in the Network.
     */
    public OrderChoice(BayesianNetwork bn, String queryVariable) {
        this.bn = bn;
        this.queryVariable = queryVariable;
        createUndirectedGraph();
    }

    /**
     * Searches the BayesianNetwork field and determines an order based on the algorithm pattern.
     * @param algo either max cardinality search ("MAX") or greedy ("GREEDY")
     * @return calculated order in String format.
     */
    public String[] search(String algo) {
        ArrayList<Node> nodes = this.bn.getNodes();
        ArrayList<Node> order = determineOrder(nodes, algo);

        return processOrder(order, this.queryVariable, algo);
    }

    /**
     * Loops through nodes and based on the algo choice determines an order.
     * @param nodes list of Nodes to create an order from.
     * @param algo determines whether the search is max cardinality or greedy.
     */
    private ArrayList<Node> determineOrder(ArrayList<Node> nodes, String algo) {
        ArrayList<Node> unmarked = new ArrayList<>(nodes);
        ArrayList<Node> marked = new ArrayList<>();
        ArrayList<Node> order = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            Node nd;
            if (algo.equals("MAX")) {
                nd = getMostMarkedNeighboursNode(unmarked, marked);
            } else {
                nd = getMinNeighboursNode(unmarked);
            }

            order.add(nd);
            unmarked.remove(nd);
            marked.add(nd);

            if (algo.equals("GREEDY")) {
                ArrayList<Node> nds = new ArrayList<>(List.of(nd));
                addLinksBetweenParents(nds);
            }
        }
        return order;
    }

    /**
     * Finds the Node in a list which has the most neighbours present in the marked list.
     * @param unmarked list of Nodes to loop through and return from.
     * @param marked list of marked Nodes to be checked against.
     * @return Node from unmarked with the most neighbours in marked.
     */
    public Node getMostMarkedNeighboursNode(ArrayList<Node> unmarked, ArrayList<Node> marked) {
        int maxMarkedNeighbours = -1;
        Node returnNode = null;

        for (Node node : unmarked) {
            ArrayList<Node> parents = node.getParents();
            int markedNeighbours = 0;

            for (Node parent : parents) {
                if (marked.contains(parent)) {
                    markedNeighbours++;
                }
            }

            if (markedNeighbours > maxMarkedNeighbours) {
                returnNode = node;
                maxMarkedNeighbours = markedNeighbours;
            }
        }

        return returnNode;
    }

    /**
     * Finds the Node in the list with the fewest number of Neighbours.
     * @param unmarked list of Nodes to search through.
     * @return Node with the fewest neighbours.
     */
    public Node getMinNeighboursNode(ArrayList<Node> unmarked) {
        int maxNumNeighbours = 10000;
        Node returnNode = null;

        for (Node node : unmarked) {
            ArrayList<Node> parents = node.getParents();
            int numNeighbours = parents.size();

            if (numNeighbours < maxNumNeighbours) {
                returnNode = node;
                maxNumNeighbours = numNeighbours;
            }
        }

        return returnNode;
    }

    /**
     * Takes a list of Nodes in a determined order, removes the query variable and then converts the list to a String array of labels.
     * If max cardinality search has taken place then also reverse the list.
     * @param order list of Nodes in calculated order.
     * @param queryVariable variable being queried in network.
     * @return processed order in String array.
     */
    public String[] processOrder(ArrayList<Node> order, String queryVariable, String algo) {
        if (algo.equals("MAX")) {
            Collections.reverse(order);
        }
        order.remove(bn.getNode(queryVariable));
        ArrayList<String> labels = bn.getLabelList(order);
        String[] orderArray = new String[labels.size()];
        orderArray = labels.toArray(orderArray);
        return orderArray;
    }

    /**
     * Takes the BayesianNetwork field and adds parents between all already linked Nodes and links unlinked co-parents.
     * This way we can in effect create an undirected graph with an existing BayesianNetwork.
     */
    public void createUndirectedGraph() {
        ArrayList<Node> nodes = this.bn.getNodes();

        addLinksBetweenParents(nodes);
        addParentForEachChild(nodes);
        dedupeParentLists(nodes);
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
     * Takes an arraylist of Nodes and for each Node, removes any duplicated parents leaving only unique Nodes.
     * @param nodes Nodes to iterate across.
     */
    public void dedupeParentLists(ArrayList<Node> nodes) {
        for (Node node : nodes) {
            ArrayList<Node> parents = node.getParents();
            ArrayList<Node> unique = new ArrayList<>();
            Set<Node> uniqueValues = new HashSet<>();
            for (Node parent : parents) {
                if (uniqueValues.add(parent)) {
                    unique.add(parent);
                }
            }
            node.setParents(unique);
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
        getKCombinations(initialSet, subsetSize, 0, new HashSet<>(), combinations);
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
