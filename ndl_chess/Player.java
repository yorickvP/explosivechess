package ndl_chess;

import java.util.ArrayList;

/**
 * The computer that is playing will be documented here. This will decide the
 * next move.
 *
 * @author new_devices_lab_4
 */
public class Player {

    private boolean color;

    public Player(boolean c) {
        color = c;
    }

    public Move decideMove(Board board) {
        int plyCount = 3;
        return playBestMove(plyCount,board);
    }

    private Move playBestMove(int plyCount, Board board) {
        ArrayList<Move> moves = board.getLegalMoves(color);
        System.out.println(moves);
        Move bestMove = moves.get(0);
        double best = (color ? -1000 : 1000);
        for (Move move : moves) {
            Board newBoard = board.copyBoard();
            newBoard.movePiece(move);
            double temp = findBestMove(plyCount - 1, newBoard);
            if (color) {
                if (temp > best) {
                    best = temp;
                    bestMove = new Move(move.getFrom(), move.getTo());
                }
            } else if (temp < best) {
                best = temp;
                bestMove = new Move(move.getFrom(), move.getTo());
            }
        }
        return bestMove;
    }

    private double evaluate(Board board) {
        return 0;
    }

    private double findBestMove(int plyCount, Board board) {
        if (plyCount == 0) {
            return evaluate(board);
        }
        ArrayList<Move> moves = board.getLegalMoves(color);
        double best = (color ? -1000 : 1000);
        for (Move move : moves) {
            Board newBoard = board.copyBoard();
            newBoard.movePiece(move);
            double temp = findBestMove(plyCount - 1, newBoard);
            if (color) {
                if (temp > best) {
                    best = temp;
                }
            } else if (temp < best) {
                best = temp;
            }
        }
        return best;
    }
}
