package ndl_chess;

import java.util.ArrayList;

/**
 *
 * @author newdeviceslab_2
 */
class Queen extends Piece {

    public Queen(boolean b) {
        color = b;
        hasMoved = false;
        moves = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            moves.add(new Point(0, i));
            moves.add(new Point(i, i));
            moves.add(new Point(i, 0));
            moves.add(new Point(i, -1*i));
            moves.add(new Point(0, -1*i));
            moves.add(new Point(-1*i, -1*i));
            moves.add(new Point(-1*i, 0));
            moves.add(new Point(-1*i, i));
        }
    }

    @Override
    public String toString() {
        if(color)
            return "Q";
        return "q";
    }

}
