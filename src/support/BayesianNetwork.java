package support;

import java.util.ArrayList;
import java.util.Arrays;

public class BayesianNetwork {

    private String name;
    private ArrayList<Node> nodes = new ArrayList<>();

    public BayesianNetwork(String name) {
        this.name = name;
    }

    public void addNode(Node ... node) {
        this.nodes.addAll(Arrays.asList(node));
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node getNode(String label) {
        for (Node node : nodes) {
            if (node.getLabel().equals(label)) {
                return node;
            }
        }
        return null;
    }

    public void printNetwork() {
        System.out.println("Network: " + this.name);
        for (Node nd: nodes) {
            System.out.println("Node: " + nd.getLabel());
            nd.printNode();
            System.out.println();
        }
    }

    /**
     * Takes a list of Nodes and returns a list of the corresponding labels.
     * @param nodes list of Nodes.
     * @return list of labels.
     */
    public ArrayList<String> getLabelList(ArrayList<Node> nodes) {
        ArrayList<String> labels = new ArrayList<>();
        for (Node node : nodes) {
            labels.add(node.getLabel());
        }

        return labels;
    }
}
