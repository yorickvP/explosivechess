package ndl_chess;

import java.util.ArrayList;

/**
 *
 * @author newdeviceslab_2
 */
class Bishop extends Piece {

    public Bishop(boolean c) {
        color = c;
        hasMoved = false;
        moves = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            moves.add(new Point(i, i));
            moves.add(new Point(i, -1 * i));
            moves.add(new Point(-1 * i, i));
            moves.add(new Point(i * -1, i * -1));
        }
    }

    @Override
    public String toString() {
        if(color)
            return "B";
        return "b";
    }

}
