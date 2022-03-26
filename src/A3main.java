import support.BayesianNetwork;
import support.EvidenceInference;
import support.Network;
import support.OrderChoice;
import support.SimpleInference;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/********************Starter Code
 *
 * This class contains some examples on how to handle the required inputs and outputs 
 *
 * @author at258
 *
 * run with 
 * java A3main <Pn> <NID>
 *
 * Feel free to change and delete parts of the code as you prefer
 *
 */
public class A3main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        switch(args[0]) {
            case "P1": {
                Network network = new Network();
                BayesianNetwork p1BN = network.getNetwork(args[1]);
                System.out.println("Network " + args[1]);
                p1BN.printNetwork();
            } break;

            case "P2": {
                Network network = new Network();
                BayesianNetwork p2BN = network.getNetwork(args[1]);
                String[] query = getQueriedNode(sc);
                String variable = query[0];
                String value = query[1];
                String[] order = getOrder(sc);
                SimpleInference infer = new SimpleInference();

                double result = infer.eliminate(p2BN, variable, value, order, null);
                printResult(result);
            } break;

            case "P3": {
                Network network = new Network();
                BayesianNetwork p3BN = network.getNetwork(args[1]);

                String[] query = getQueriedNode(sc);
                String variable = query[0];
                String value = query[1];
                String[] order = getOrder(sc);
                ArrayList<String[]> evidence = getEvidence(sc);
                EvidenceInference infer = new EvidenceInference();

                double result = infer.eliminate(p3BN, variable, value, order, evidence);
                printResult(result);
            } break;

            case "P4": {
                Network network = new Network();
                BayesianNetwork p4BN = network.getNetwork(args[1]);

                String[] query = getQueriedNode(sc);
                String variable = query[0];
                String value = query[1];

                OrderChoice oc = new OrderChoice(p4BN, variable);
                String[] order = oc.search(args[2]);
                ArrayList<String[]> evidence = getEvidence(sc);

                BayesianNetwork p4CleanBN = network.getNetwork(args[1]);
                EvidenceInference infer = new EvidenceInference();

                System.out.println(Arrays.toString(order));
                double result = infer.eliminate(p4CleanBN, variable, value, order, evidence);
                printResult(result);
            } break;
        }
        sc.close();
    }


    /**
     * Method to obtain the evidence from the user.
     * @param sc scanner to use.
     * @return Arraylist of evidence arrays mapping a variable name to a T/F value.
     */
    private static ArrayList<String[]> getEvidence(Scanner sc) {

        System.out.println("Evidence:");
        ArrayList<String[]> evidence = new ArrayList<String[]>();
        String[] line = sc.nextLine().split(" ");

        for(String st:line) {
            String[] ev = st.split(":");
            evidence.add(ev);
        }
        return evidence;
    }

    /**
     * Method to obtain the order from the user.
     * @param sc scanner to use.
     * @return Array of variable names in order of elimination.
     */
    private static String[] getOrder(Scanner sc) {

        System.out.println("Order:");
        return sc.nextLine().split(",");
    }

    /**
     * Method to obtain the queried node from the user.
     * @param sc scanner to use.
     * @return Array containing query variable and requested value.
     */
    private static String[] getQueriedNode(Scanner sc) {

        System.out.println("Query:");
        return sc.nextLine().split(":");

    }

    /**
     * Method to format and print the result.
     * @param result probability of request.
     */
    private static void printResult(double result) {

        DecimalFormat dd = new DecimalFormat("#0.00000");
        System.out.println(dd.format(result));
    }
}
