/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ndl;

import java.util.ArrayList;

/**
 *
 * @author newdeviceslab_2
 */
class King extends Piece {


    public King(boolean b) {
        color = b;
        nrOfMoves = 0;
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
    
    public King(boolean b, int nrOfMoves) {
        color = b;
        this.nrOfMoves = nrOfMoves;
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

    @Override
    public boolean equals(Object o) {
          if (o == null || getClass() != o.getClass()) {
            return false;
        }
        King p = (King) o;
        return color == p.color && nrOfMoves > 0 == p.nrOfMoves > 0;
    }
    
    @Override
    public King copyPiece() {
        return new King(color, nrOfMoves);
    }

}
