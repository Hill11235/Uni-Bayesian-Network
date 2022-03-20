package support;

import java.util.ArrayList;

//TODO implement bn class using guidance in the notes
public class BayesianNetwork {

    private String name;
    private ArrayList<Node> nodes = new ArrayList<>();

    public BayesianNetwork(String name) {
        this.name = name;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void printNetwork() {
        for (Node nd: nodes) {
            System.out.println(nd.getLabel());
            nd.printNode();
            System.out.println();
        }
    }
    //print network method
}
