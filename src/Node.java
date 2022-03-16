import java.util.ArrayList;
import java.util.HashSet;

public class Node {

    ArrayList<Node> parents;
    HashSet<Node> children;
    String label;
    int CPTrows = 0;

    public Node(String label, ArrayList<Node> parents) {
        this.label = label;
        this.parents = parents;
    }

    //TODO implement so that CPT field is updated
    public void addCPTvalues(double ... vals) {

    }

    //TODO update to reflect own implementation
    public void printNode() {
        int var = 2;
        int size=(int) Math.pow(2,var);
        for (int i = 0; i < size; i++) {
            int repeat = var - Integer.toBinaryString(i).length();
            String truths = "0".repeat(repeat) + Integer.toBinaryString(i);
            for (char c : truths.toCharArray()) {
                System.out.print(c+"\t");
            }
            System.out.println("|" );
        }
    }
}
