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
class Pawn extends Piece {

    public Pawn(boolean c) {
        color = c;
        nrOfMoves = 0;
        moves = new ArrayList<>();
        int dir = (c ? -1 : 1);
        moves.add(new Point(dir, 0));
        moves.add(new Point(2 * dir, 0));
        moves.add(new Point(dir, -1));
        moves.add(new Point(dir, 1));
    }
    
    public Pawn(boolean c, int nrOfMoves) {
        color = c;
        this.nrOfMoves = nrOfMoves;
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
        return color == p.color && nrOfMoves > 0 == p.nrOfMoves > 0;
    }
    
    @Override
    public Pawn copyPiece() {
        return new Pawn(color, nrOfMoves);
    }

}
