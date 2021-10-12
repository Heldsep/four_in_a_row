package NRow.Players;
import java.util.List;

import NRow.Board;
import NRow.Node;
import NRow.Heuristics.Heuristic;

public class AlphaBetaPlayer extends PlayerController {
    private int depth;

    public AlphaBetaPlayer(int playerId, int gameN, int depth, Heuristic heuristic) {
        super(playerId, gameN, heuristic);
        this.depth = depth;
        //You can add functionality which runs when the player is first created (before the game starts)
    }

    /**
   * Implement this method yourself!
   * @param board the current board
   * @return column integer the player chose
   */
    @Override
    public int makeMove(Board board) {
        Node root=new Node(board, null, true, -1);
        createTree(root, depth); // make a tree of given depth and assign the return Node to root
        int best = miniMaxAlphaBeta(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return root.findChildWith(best).getPlayedColumn();
    }


    public void createTree(Node node, int numberOfLayers){
        if(numberOfLayers == 0){
            return;
        }
        else if (numberOfLayers > 1){
            makeChildren(node, false);
            for (Node child : node.getChildren()) {
                createTree(child, numberOfLayers-1);
            }
        } else if (numberOfLayers == 1) {
            makeChildren(node, true);
            return;
        }
        return;
    }

    /**
   * Fills the empty list of children with all possible gamestates and their evaluation
   * @param parent the current gamestate as Node
   */
    public void makeChildren(Node parent, boolean leaves){
        Board board = parent.getState();

        int id;
        if(parent.isMaxPlayer()){
            id = playerId;
        } else{
            if(playerId == 1){
                id = 2;
            }else{
                id = 1;
            }
        }  

        for(int i = 0; i < board.width; i++) { //for each of the possible moves
            if(board.isValid(i)) { //if the move is valid
                Board newBoard = board.getNewBoard(i, id); // Get a new board resulting from that move
                if(leaves){
                    int value = heuristic.evaluateBoard(playerId, newBoard); //evaluate that new board to get a heuristic value from it
                    parent.addChild(new Node(newBoard, parent, !parent.isMaxPlayer(), value, i));
                } else {
                parent.addChild(new Node(newBoard, parent, !parent.isMaxPlayer(), i)); // Add the new board to the list of children
                }
            }
        }
    }
    
    private int miniMaxAlphaBeta(Node node, int alpha, int beta) {
        if (node.isLeaf()) {return node.getHeuristic();}        
        List<Node> children = node.getChildren();
        if(node.isMaxPlayer()){
            int maxEva = Integer.MIN_VALUE;
            for (Node child : children) {
                int eval = miniMaxAlphaBeta(child, alpha, beta);
                maxEva = Integer.max(maxEva,eval);
                alpha = Integer.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            node.setHeuristic(maxEva);
            return maxEva;
        } else {
            int minEva = Integer.MAX_VALUE;
            for (Node child : children) {
                int eval = miniMaxAlphaBeta(child, alpha, beta);
                minEva = Integer.min(minEva,eval);
                beta = Integer.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            node.setHeuristic(minEva);
            return minEva;
        }
    }
}


