package support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO implement static Support.BayesianNetwork objects for each of the four needed networks
public class Network {

    public BayesianNetwork BNA;
    public BayesianNetwork BNB;
    public BayesianNetwork BNC;

    public Network() {
        initiateBNA();
    }

    //TODO BNA
    private void initiateBNA() {
        Node A = new Node("A", null);
        Node B = new Node("B", new ArrayList<>(List.of(A)));
        Node C = new Node("C", new ArrayList<>(List.of(B)));
        Node D = new Node("D", new ArrayList<>(List.of(C)));

        A.addCPTvalues(0.95, 0.05);
        B.addCPTvalues(0.20, 0.80, 0.95, 0.05);
        C.addCPTvalues(0.70, 0.30, 0.90, 0.10);
        D.addCPTvalues(0.40, 0.60, 0.60, 0.40);

        this.BNA = new BayesianNetwork("BNA");
        this.BNA.addNode(A, B, C, D);
    }

    //TODO BNB
    private void initiateBNB() {

    }

    //TODO BNC
    private void initiateBNC() {

    }
}
