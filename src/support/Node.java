package support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents each Node in the network.
 */
public class Node {

    private ArrayList<Node> parents = new ArrayList<>();
    private HashSet<Node> children = new HashSet<>();
    private String label;
    private int CPTrows = 2;
    private int numParents = 0;
    private Factor cpt;

    /**
     * Constructor for Node.
     * @param label label associated with this Node.
     * @param parents parent Nodes of this Node.
     */
    public Node(String label, ArrayList<Node> parents) {
        this.label = label;
        initiateFactor(parents);
        this.CPTrows = cpt.getNumRows();
        if (parents != null) {
            this.parents = parents;
            numParents = parents.size();
            for (Node parent : parents) {
                parent.addChildren(this);
            }
        }
    }

    /**
     * Initialise Factor object and add all labels associated with this Node.
     * @param parents parent Nodes.
     */
    private void initiateFactor(ArrayList<Node> parents) {
        ArrayList<String> labels = new ArrayList<>();

        if (parents != null) {
            for (Node parent : parents) {
                labels.add(parent.getLabel());
            }
        }
        labels.add(this.label);
        this.cpt = new Factor(labels);
    }

    /**
     * Add the probabilities to this Node's associated Factor.
     * @param vals vararg of probabilities.
     */
    public void addCPTvalues(double ... vals) {
        this.cpt.addProbabilities(vals);
    }

    /**
     * Add child Nodes.
     * @param children vararg of child Nodes.
     */
    public void addChildren(Node ... children) {
        this.children.addAll(Arrays.asList(children));
    }

    /**
     * Calculates the preceding zeros by taking number of Nodes referenced - binary version of i length.
     * Combine the repeated zeros and binary representation of i.
     * Loop through this String and print all the individual characters.
     * This will capture all the T/F permutations for all parent Nodes and this Node.
     */
    public void printNode() {
        printHeader();
        HashMap<String, Double> probabilities = this.cpt.getProbabilities();
        for (int i = 0; i < this.CPTrows; i++) {
            int repeat = (numParents + 1) - Integer.toBinaryString(i).length();
            String truths = "0".repeat(repeat) + Integer.toBinaryString(i);
            for (char c : truths.toCharArray()) {
                System.out.print(c + "\t");
            }
            System.out.print("|" + "\t");
            Double prob = probabilities.get(truths);
            System.out.println(prob);
        }
    }

    /**
     * Print the column headers in printed CPT table.
     */
    private void printHeader() {
        StringBuilder conditions = new StringBuilder();

        for (int i = 0; i < numParents; i++) {
            Node parent = parents.get(i);
            System.out.print(parent.getLabel() + "\t");
            conditions.append(parent.getLabel());
            if (i != parents.size() - 1) {
                conditions.append(",");
            }
        }

        System.out.print(this.label + "\t");

        String condProb = createConditional(conditions);
        System.out.println("|" + "\t" + condProb);
    }

    /**
     * Creates conditional probability header to be printed.
     * @param conditions StringBuilder of all conditions.
     * @return String conditional/single probability.
     */
    private String createConditional(StringBuilder conditions) {
        if (conditions.length() == 0) {
            return "p(" + this.label+ ")";
        }
        return "p(" + this.label + "|" + conditions + ")";
    }

    /**
     * Get list of parent Nodes.
     * @return list of parent Nodes.
     */
    public ArrayList<Node> getParents() {
        return parents;
    }

    /**
     * Set parent Nodes with ArrayList, used for the creation of undirected graphs.
     * @param parents arraylist of parents to be set.
     */
    //TODO test
    public void setParents(ArrayList<Node> parents) {
        this.parents = parents;
    }

    /**
     * Get label associated with this Node.
     * @return String version of label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get set of child Nodes.
     * @return set of children.
     */
    public HashSet<Node> getChildren() {
        return children;
    }

    /**
     * Getter for CPT.
     * @return Factor object.
     */
    public Factor getCpt() {
        return cpt;
    }

    /**
     * Given a Node, return a list of all of its ancestor Nodes.
     * @return list of ancestors.
     */
    public ArrayList<Node> getAllAncestors() {
        ArrayList<Node> frontier = new ArrayList<>();
        ArrayList<Node> ancestors = new ArrayList<>();

        if (parents != null) {
            frontier.addAll(parents);
        }

        while (frontier.size() > 0) {
            Node nd = frontier.remove(0);
            ancestors.add(nd);
            if (nd.getParents() != null) {
                frontier.addAll(nd.getParents());
            }
        }

        return ancestors;
    }

    /**
     * Add additional parent to this Node.
     * Used for creating undirected graph.
     * @param parent Node to be added as a parent.
     */
    public void addParent(Node parent) {
        this.parents.add(parent);
    }
}
