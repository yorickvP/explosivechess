package ndl_chess;

import java.util.ArrayList;

/**
 *
 * @author newdeviceslab_2
 */
class Rook extends Piece {


    public Rook(boolean c) {
        color = c;
        hasMoved = false;
        moves = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            moves.add(new Point(0, i));
            moves.add(new Point(i, 0));
            moves.add(new Point(0, -1 * i));
            moves.add(new Point(-1 * i, 0));
        }
    }

    @Override
    public String toString() {
        if(color)
            return "R";
        return "r";
    }

    public void setMoved() {
        hasMoved = true;
    }

    public boolean isMoved() {
        return hasMoved;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rook p = (Rook) o;
        return color == p.color && hasMoved == p.hasMoved;
    }

}
