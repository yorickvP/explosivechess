package ndl_chess;
import java.util.ArrayList;

/**
 *
 * @author newdeviceslab_2
 */
public class Piece {

    /**
     *
     */
    protected ArrayList<Point> moves;
    protected boolean color;
    protected boolean hasMoved;

    public boolean getColor() {
        return color;
    }

    public ArrayList<Point> getMoves() {
        ArrayList<Point> candMoves = new ArrayList<>();
        for (Point move : moves) {
            candMoves.add(new Point(move.getX(), move.getY()));
        }
        return candMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece p = (Piece) o;
        return color == p.color;
    }

    public void setMoved() {
        hasMoved = true;
    }

    public boolean isMoved() {
        return hasMoved;
    }
}
