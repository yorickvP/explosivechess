package ndl_chess;

import java.util.ArrayList;

/**
 *
 * @author newdeviceslab_2
 */
public class Knight extends Piece {
    
    
    public Knight(boolean c) {
        color = c;
        hasMoved = false;
        moves = new ArrayList<>();
        moves.add(new Point(1,2));
        moves.add(new Point(1,-2));
        moves.add(new Point(-1,2));
        moves.add(new Point(-1,-2));
        moves.add(new Point(2,1));
        moves.add(new Point(2,-1));
        moves.add(new Point(-2,1));
        moves.add(new Point(-2,-1));
    }
    
    
    @Override
    public String toString() {
        if(color)
            return "N";
        return "n";
    }
    
}
