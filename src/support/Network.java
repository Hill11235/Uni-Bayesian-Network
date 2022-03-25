package support;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of hardcoded networks for problems.
 */
public class Network {

    public BayesianNetwork CNX;
    public BayesianNetwork BNA;
    public BayesianNetwork BNB;
    public BayesianNetwork BNC;
    public BayesianNetwork BND;

    /**
     * Constructor, initialises networks.
     */
    public Network() {
        initiateCNX();
        initiateBNA();
        initiateBNB();
        initiateBNC();
        initiateBND();
    }

    /**
     * Initialises network for problem defined in P1.
     */
    //TODO finalise and implement CNX network
    private void initiateCNX() {

    }

    /**
     * Initialises BNA network.
     */
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

    /**
     * Initialises BNB network.
     */
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

    /**
     * Initialises BNC network.
     */
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

    /**
     * Initialises BND network. This is an extension network for P5.
     */
    private void initiateBND() {
        //winter W, storm S, engineering work E, train delay D, road works R, bus delay B, late L.
        Node W = new Node("W", null);
        Node S = new Node("S", new ArrayList<>(List.of(W)));
        Node E = new Node("E", new ArrayList<>(List.of(S)));
        Node D = new Node("D", new ArrayList<>(List.of(S, E)));
        Node R = new Node("R", null);
        Node B = new Node("B", new ArrayList<>(List.of(R)));
        Node L = new Node("L", new ArrayList<>(List.of(D, B)));

        W.addCPTvalues(0.75, 0.25);
        S.addCPTvalues(0.98, 0.02, 0.8, 0.2);
        E.addCPTvalues(0.99, 0.01, 0.9, 0.1);
        D.addCPTvalues(0.95, 0.05, 0.20, 0.80, 0.30, 0.70, 0.05, 0.95);
        R.addCPTvalues(0.99, 0.01);
        B.addCPTvalues(0.90, 0.10, 0.2, 0.8);
        L.addCPTvalues(0.96, 0.04, 0.3, 0.7, 0.1, 0.9, 0.05, 0.95);

        this.BND = new BayesianNetwork("BND");
        this.BND.addNode(W, S, E, D, R, B, L);
    }

    /**
     * Returns the appropriate network based on the command line arguments.
     */
    public BayesianNetwork getNetwork(String request) {
        switch (request) {
            case "BNA":
                return this.BNA;
            case "BNB":
                return this.BNB;
            case "BNC":
                return this.BNC;
            case "BND":
                return this.BND;
            default:
                return this.CNX;
        }
    }
}
