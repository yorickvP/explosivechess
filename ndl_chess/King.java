package ndl_chess;

import java.util.ArrayList;

/**
 *
 * @author newdeviceslab_2
 */
class King extends Piece {


    public King(boolean b) {
        color = b;
        hasMoved = false;
        moves = new ArrayList<>();
        moves.add(new Point(0, 1));
        moves.add(new Point(1, 1));
        moves.add(new Point(1, 0));
        moves.add(new Point(1, -1));
        moves.add(new Point(0, -1));
        moves.add(new Point(-1, -1));
        moves.add(new Point(-1, 0));
        moves.add(new Point(-1, 1));
    }

    @Override
    public String toString() {
        if(color)
            return "K";
        return "k";
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
        King p = (King) o;
        return color == p.color && hasMoved == p.hasMoved;
    }

}
