package support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Node {

    private ArrayList<Node> parents;
    private HashSet<Node> children = new HashSet<>();
    private String label;
    private int CPTrows = 2;
    private Factor cpt;

    public Node(String label, ArrayList<Node> parents) {
        this.label = label;
        this.parents = parents;
        initiateFactor(parents);
        if (parents != null) {
            this.CPTrows = cpt.getNumRows();
            for (Node parent : parents) {
                parent.addChildren(this);
            }
        }
    }

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

    public void addCPTvalues(double ... vals) {
        this.cpt.addProbabilities(vals);
    }

    public void addChildren(Node ... children) {
        this.children.addAll(Arrays.asList(children));
    }

    //TODO update to reflect own implementation

    /**
     * Calculates the preceding zeros by taking number of Nodes referenced - binary version of i length.
     * Combine the repeated zeros and binary representation of i.
     * Loop through this String and print all the individual characters.
     * This will capture all the T/F permutations for all parent Nodes and this Node.
     */
    public void printNode() {
        printHeader();
        ArrayList<Double> probabilities = this.cpt.getProbabilities();
        for (int i = 0; i < this.CPTrows; i++) {
            int repeat = (parents.size() + 1) - Integer.toBinaryString(i).length();
            String truths = "0".repeat(repeat) + Integer.toBinaryString(i);
            for (char c : truths.toCharArray()) {
                System.out.print(c + "\t");
            }
            System.out.print("|" + "\t");
            Double prob = probabilities.get(i);
            System.out.println(prob);
        }
    }

    private void printHeader() {
        StringBuilder conditions = new StringBuilder();

        for (int i = 0; i < parents.size(); i++) {
            Node parent = parents.get(i);
            System.out.print(parent.getLabel() + "\t");
            conditions.append(parent.getLabel());
            if (i != parents.size() - 1) {
                conditions.append(",");
            }
        }
        System.out.print(this.label + "\t");

        String condProb = "p(" + this.label + "|" + conditions + ")";
        System.out.println("|" + "\t" + condProb);
    }

    public ArrayList<Node> getParents() {
        return parents;
    }

    public String getLabel() {
        return label;
    }

    public HashSet<Node> getChildren() {
        return children;
    }

    public Factor getCpt() {
        return cpt;
    }
}
