package support;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to represent Bayesian networks. Essentially collects Nodes together.
 */
public class BayesianNetwork {

    private final String name;
    private final ArrayList<Node> nodes = new ArrayList<>();

    /**
     * Constructor for class.
     * @param name the name of the network.
     */
    public BayesianNetwork(String name) {
        this.name = name;
    }

    /**
     * Method to add Node(s).
     * @param node Node(s) to be added to network.
     */
    public void addNode(Node ... node) {
        this.nodes.addAll(Arrays.asList(node));
    }

    /**
     * Get Nodes in this network.
     * @return arraylist of Nodes in the network.
     */
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    /**
     * Get Node from network based on its label.
     * @param label label of Node to be retrieved.
     * @return requested Node.
     */
    public Node getNode(String label) {
        for (Node node : nodes) {
            if (node.getLabel().equals(label)) {
                return node;
            }
        }
        return null;
    }

    /**
     * P1 requirement - print the network Node by Node.
     */
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
