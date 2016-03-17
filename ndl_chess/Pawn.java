package ndl_chess;

import java.util.ArrayList;

/**
 *
 * @author newdeviceslab_2
 */
class Pawn extends Piece {

    public Pawn(boolean c) {
        color = c;
        hasMoved = false;
        moves = new ArrayList<>();
        int dir = (c ? -1 : 1);
        moves.add(new Point(dir, 0));
        moves.add(new Point(2 * dir, 0));
        moves.add(new Point(dir, -1));
        moves.add(new Point(dir, 1));
        
    }

    @Override
    public String toString() {
        if(color)
            return "P";
        return "p";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pawn p = (Pawn) o;
        return color == p.color && hasMoved == p.hasMoved;
    }

}
