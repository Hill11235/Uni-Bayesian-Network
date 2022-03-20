package support;

import java.util.ArrayList;
import java.util.List;

public class Network {

    public BayesianNetwork CNX;
    public BayesianNetwork BNA;
    public BayesianNetwork BNB;
    public BayesianNetwork BNC;

    public Network() {
        initiateCNX();
        initiateBNA();
        initiateBNB();
        initiateBNC();
    }

    //TODO finalise and implement CNX network
    private void initiateCNX() {

    }

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

    private void initiateBNB() {
        Node J = new Node("J", null);
        Node K = new Node("K", new ArrayList<>(List.of(J)));
        Node L = new Node("L", null);
        Node M = new Node("M", new ArrayList<>(List.of(K, L)));
        Node N = new Node("N", new ArrayList<>(List.of(M)));
        Node O = new Node("O", new ArrayList<>(List.of(M)));

        J.addCPTvalues(0.95, 0.05);
        K.addCPTvalues(0.30, 0.70, 0.10, 0.90);
        L.addCPTvalues(0.30, 0.70);
        M.addCPTvalues(0.90, 0.10, 0.80, 0.20, 0.30, 0.70, 0.40, 0.60);
        N.addCPTvalues(0.80, 0.20, 0.40, 0.60);
        O.addCPTvalues(0.20, 0.80, 0.95, 0.05);

        this.BNB = new BayesianNetwork("BNB");
        this.BNB.addNode(J, K, L, M, N, O);
    }

    private void initiateBNC() {
        Node P = new Node("P", null);
        Node Q = new Node("Q", new ArrayList<>(List.of(P)));
        Node R = new Node("R", null);
        Node S = new Node("S", new ArrayList<>(List.of(Q, R)));
        Node U = new Node("U", new ArrayList<>(List.of(S)));
        Node V = new Node("V", new ArrayList<>(List.of(Q, R)));
        Node Z = new Node("Z", new ArrayList<>(List.of(V, S)));

        P.addCPTvalues(0.95, 0.05);
        Q.addCPTvalues(0.30, 0.70, 0.10, 0.90);
        R.addCPTvalues(0.30, 0.70);
        S.addCPTvalues(0.90, 0.10, 0.80, 0.20, 0.30, 0.70, 0.40, 0.60);
        U.addCPTvalues(0.20, 0.80, 0.95, 0.05);
        V.addCPTvalues(0.90, 0.10, 0.85, 0.15, 0.45, 0.55, 0.30, 0.70);
        Z.addCPTvalues(0.80, 0.20, 0.60, 0.40, 0.30, 0.70, 0.35, 0.65);

        this.BNC = new BayesianNetwork("BNC");
        this.BNC.addNode(P, Q, R, S, U, V, Z);
    }
}
