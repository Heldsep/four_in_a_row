package NRow;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Node {
    private Board state;
    private Node parent;
    private ArrayList<Node> children;
    private int heuristic;
    private boolean isMaxPlayer;
    private int playedColumn;

    public Node(Board state, Node parent, boolean isMaxPlayer, int playedColumn){
        this.state = state;
        this.parent = parent;
        this.children = new ArrayList<Node>();
        this.isMaxPlayer = isMaxPlayer;
        this.playedColumn = playedColumn;
    }

    public Node(Board state, Node parent, boolean isMaxPlayer, int value, int playedColumn){
        this.state = state;
        this.parent = parent;
        this.children = new ArrayList<Node>();
        this.isMaxPlayer = isMaxPlayer;
        this.heuristic = value;
        this.playedColumn = playedColumn;
    }

    public void addChild(Node child){
        children.add(child);
    }

    public Board getState(){
        return state;
    }

    public ArrayList<Node> getChildren(){
        return children;
    }

    public int getHeuristic(){
        return heuristic;
    }

    public void setHeuristic(int heuristic){
        this.heuristic = heuristic;
    }

    public boolean isMaxPlayer(){
        return isMaxPlayer;
    }

    public Node findBestChild(){
        Comparator<Node> byHeurComparator = Comparator.comparing(Node::getHeuristic);
        return children.stream()
                .max(isMaxPlayer ? byHeurComparator : byHeurComparator.reversed())
                .orElseThrow(NoSuchElementException::new);
    }

    public Node findChildWith(int heuristic){
        for (Node child : children) {
            if(child.getHeuristic() == heuristic){
                return child;
            }
        }
        return null;
    }

    public boolean isLeaf(){
        return children.isEmpty();
    }

    public int getPlayedColumn(){
        return this.playedColumn;
    }

}
