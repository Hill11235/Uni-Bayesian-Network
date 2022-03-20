package support;

import java.util.ArrayList;
import java.util.Arrays;

//TODO implement bn class using guidance in the notes
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

    public void printNetwork() {
        System.out.println("Network: " + this.name);
        for (Node nd: nodes) {
            System.out.println("Node: " + nd.getLabel());
            nd.printNode();
            System.out.println();
        }
    }
}
