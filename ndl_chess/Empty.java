package ndl_chess;

import java.util.ArrayList;

/**
 *
 * @author newdeviceslab_2
 */
public class Empty extends Piece {

    public Empty() {
        color = true;
        hasMoved = false;
        moves = new ArrayList<Point>();
    }
    
    @Override
    public String toString() {
        return " ";
    }
}
