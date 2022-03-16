package support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Node {

    private ArrayList<Node> parents;
    private HashSet<Node> children;
    private String label;
    private int CPTrows = 1;

    public Node(String label, ArrayList<Node> parents) {
        this.label = label;
        this.parents = parents;
        if (parents != null) {
            this.CPTrows = (int) Math.pow(2,parents.size());
        }
    }

    //TODO implement so that CPT field is updated
    public void addCPTvalues(double ... vals) {

    }

    public void addChildren(Node ... children) {
        this.children.addAll(Arrays.asList(children));
    }

    //TODO update to reflect own implementation
    public void printNode() {
        for (int i = 0; i < this.CPTrows; i++) {
            int repeat = parents.size() - Integer.toBinaryString(i).length();
            String truths = "0".repeat(repeat) + Integer.toBinaryString(i);
            for (char c : truths.toCharArray()) {
                System.out.print(c + "\t");
            }
            System.out.println("|" );
        }
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
}
